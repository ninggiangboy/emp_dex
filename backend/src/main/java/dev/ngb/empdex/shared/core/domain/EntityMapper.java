package dev.ngb.empdex.shared.core.domain;

public interface EntityMapper<T extends AggregateRoot<?>, E> {
    T toDomain(E entity);

    E toEntity(T domain);
}