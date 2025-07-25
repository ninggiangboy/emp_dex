package dev.ngb.empdex.user.internal.infrastructure.repository;

import dev.ngb.empdex.shared.core.base.Page;
import dev.ngb.empdex.shared.core.base.Pageable;
import dev.ngb.empdex.user.internal.dto.UserSummaryDto;
import dev.ngb.empdex.user.internal.domain.repository.UserReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserReadRepositoryImpl implements UserReadRepository {
    private final UserMyBatisRepository userMyBatisRepository;

    @Override
    public Page<UserSummaryDto> findAllUsers(Pageable pageable, String keyword, String status) {
        Collection<UserSummaryDto> users = userMyBatisRepository.findAllUsers(
                keyword, status,
                pageable.sortField(),
                pageable.sortDirection().getValue(),
                pageable.offset(),
                pageable.pageSize()
        );
        if (users.size() < pageable.pageSize() && pageable.pageNumber() == 0) {
            return Page.singlePage(users, pageable.pageSize());
        }
        long totalCount = userMyBatisRepository.countUsers(keyword, status);
        return Page.of(users, pageable.pageNumber(), pageable.pageSize(), totalCount);
    }
}
