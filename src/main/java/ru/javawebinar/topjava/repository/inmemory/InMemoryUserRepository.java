package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private final static Comparator<User> comparator = new usersNameEmailComparator<>();

    {
        UsersUtil.users.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user == null) return null;

        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
        }
        return repository.computeIfPresent(user.getId(), (id, oldValue) -> user);
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        return repository.values().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        return getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    private static class usersNameEmailComparator<T extends User> implements Comparator<T> {

        @Override
        public int compare(User user1, User user2) {
            String name1 = user1.getName();
            String name2 = user2.getName();
            if (name1.equals(name2)) {
                return name1.compareTo(name2);
            } else {
                String eMail1 = user1.getEmail();
                String eMail2 = user2.getEmail();
                return eMail1.compareTo(eMail2);
            }
        }
    }
}
