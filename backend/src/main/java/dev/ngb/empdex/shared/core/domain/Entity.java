package dev.ngb.empdex.shared.core.domain;

import lombok.Getter;

@Getter
public abstract class Entity<ID> {
    protected ID id;
}
