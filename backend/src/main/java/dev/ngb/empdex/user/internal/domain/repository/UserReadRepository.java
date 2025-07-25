package dev.ngb.empdex.user.internal.domain.repository;

import dev.ngb.empdex.shared.core.base.Page;
import dev.ngb.empdex.shared.core.base.Pageable;
import dev.ngb.empdex.user.internal.dto.UserSummaryDto;

public interface UserReadRepository {
    Page<UserSummaryDto> findAllUsers(Pageable pageable, String keyword, String status);
}
