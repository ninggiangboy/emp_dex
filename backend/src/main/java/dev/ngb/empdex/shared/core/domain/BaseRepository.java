package dev.ngb.empdex.shared.core.domain;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends AggregateRoot<ID>, ID> {
    Optional<T> findById(ID id);

    List<T> findAll();

    T save(T aggregate);

    void delete(T aggregate);

    void deleteById(ID id);
}
