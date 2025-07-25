package dev.ngb.empdex.shared.core.mediator;

import dev.ngb.empdex.shared.core.base.Result;

public interface RequestHandler<T extends Request<R>, R> {
    Result<R> handle(T request);
}