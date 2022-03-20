package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final static RowMapper<Role> ROLE_ROW_MAPPER = (rs, rowNum) -> {
        String role = rs.getString("role");
        return role == null ? null : Role.valueOf(role);
    };

    private final static BeanPropertyRowMapper<User> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

    private final static ResultSetExtractor<List<User>> resultSetExtractor = rs -> {
        List<User> users = new ArrayList<>();
        User currentUser = null;
        while (rs.next()) {
            int id = rs.getInt("id");
            if (currentUser == null) { // initial object
                currentUser = USER_ROW_MAPPER.mapRow(rs, rs.getRow());
            } else if (currentUser.getId() != id) { // break
                users.add(currentUser);
                currentUser = USER_ROW_MAPPER.mapRow(rs, rs.getRow());
            }
            if (currentUser.getRoles() == null)
                currentUser.setRoles(new HashSet<>());
            Role role = ROLE_ROW_MAPPER.mapRow(rs, rs.getRow());
            if (role != null) currentUser.getRoles().add(role);
        }
        if (currentUser != null) { // last object
            users.add(currentUser);
        }
        return users;
    };

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        }
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        for (Role role : user.getRoles()) {
            jdbcTemplate.update("INSERT INTO user_roles(user_id, role) VALUES(?,?)", user.getId(), role.name());
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users u left join user_roles ur on u.id = ur.user_id WHERE u.id=?", resultSetExtractor, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users u left join user_roles ur on u.id = ur.user_id WHERE u.email=?", resultSetExtractor, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users u left join user_roles ur on u.id = ur.user_id ORDER BY u.name, u.email", resultSetExtractor);
    }
}
