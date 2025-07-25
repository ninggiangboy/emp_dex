package dev.ngb.empdex.user.internal.application.feature.user.query;

import dev.ngb.empdex.shared.core.base.Page;
import dev.ngb.empdex.shared.core.base.Pageable;
import dev.ngb.empdex.shared.core.base.Result;
import dev.ngb.empdex.user.internal.dto.UserSummaryDto;
import dev.ngb.empdex.user.internal.domain.repository.UserReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
    private final UserReadRepository userReadRepository;

    @Override
    public Result<Page<UserSummaryDto>> getAllUsers(Pageable pageable, String keyword, String status) {
        var users = userReadRepository.findAllUsers(pageable, keyword, status);
        return Result.success(users);
    }
}
