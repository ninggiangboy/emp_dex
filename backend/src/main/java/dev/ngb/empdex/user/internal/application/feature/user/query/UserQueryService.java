package dev.ngb.empdex.user.internal.application.feature.user.query;

import dev.ngb.empdex.shared.core.base.Page;
import dev.ngb.empdex.shared.core.base.Pageable;
import dev.ngb.empdex.shared.core.base.Result;
import dev.ngb.empdex.user.internal.dto.UserSummaryDto;

public interface UserQueryService {
    Result<Page<UserSummaryDto>> getAllUsers(Pageable pageable, String keyword, String status);
}
