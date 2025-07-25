package dev.ngb.empdex.shared.core.mediator;

import dev.ngb.empdex.shared.core.base.Result;

public interface Mediator {
    <R, T extends Request<R>> Result<R> send(T request);
}
