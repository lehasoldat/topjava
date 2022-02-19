package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryBaseRepository<T extends AbstractBaseEntity> {

    private final Map<Integer, T> map = new ConcurrentHashMap<>();
    private static AtomicInteger counter = new AtomicInteger(0);

    public T save(T entity) {
        if (entity == null) return null;

        if (entity.isNew()) {
            entity.setId(counter.incrementAndGet());
            map.put(entity.getId(), entity);
        }
        return map.computeIfPresent(entity.getId(), (id, oldValue) -> entity);
    }

    public boolean delete(int id) {
        return map.remove(id) != null;
    }

    public T get(int id) {
        return map.get(id);
    }

    public Collection<T> getCollection() {
        return map.values();
    }

}
