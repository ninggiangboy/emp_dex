package dev.ngb.empdex.user.rest;

import dev.ngb.empdex.shared.core.base.Page;
import dev.ngb.empdex.shared.core.base.Pageable;
import dev.ngb.empdex.shared.core.rest.Response;
import dev.ngb.empdex.user.internal.dto.UserSummaryDto;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/users")
public interface UserApi {
    @GetMapping
    Response<Page<UserSummaryDto>> getAllUsers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Pageable.Direction direction,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status
    );

    @PostMapping
    Response<UUID> createUser();
}
