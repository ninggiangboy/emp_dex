package dev.ngb.empdex.user.rest.resource;

import dev.ngb.empdex.shared.core.base.Page;
import dev.ngb.empdex.shared.core.base.Pageable;
import dev.ngb.empdex.shared.core.mediator.Mediator;
import dev.ngb.empdex.shared.core.rest.Response;
import dev.ngb.empdex.user.internal.application.feature.user.command.create_user.CreateUserCommand;
import dev.ngb.empdex.user.internal.application.feature.user.query.UserQueryService;
import dev.ngb.empdex.user.internal.dto.UserSummaryDto;
import dev.ngb.empdex.user.rest.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserResource implements UserApi {
    private final UserQueryService userQueryService;
    private final Mediator mediator;

    @Override
    public Response<Page<UserSummaryDto>> getAllUsers(Integer page, Integer size, String sort, Pageable.Direction direction, String keyword, String status) {
        var pageable = Pageable.of(page, size, sort, direction);
        var result = userQueryService.getAllUsers(pageable, keyword, status);
        return Response.create(result);
    }

    @Override
    public Response<UUID> createUser() {
        var command = new CreateUserCommand("defaultUsername", "email");
        var result = mediator.send(command);
        return Response.create(result, HttpStatus.CREATED);
    }
}
