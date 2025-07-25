package dev.ngb.empdex.user.internal.infrastructure.repository;

import dev.ngb.empdex.user.internal.dto.UserSummaryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMyBatisRepository {
    List<UserSummaryDto> findAllUsers(@Param("keyword") String keyword, @Param("status") String status, @Param("sortField") String sortField, @Param("sortDirection") String sortDirection, @Param("offset") int offset, @Param("limit") int limit);

    Long countUsers(@Param("keyword") String keyword, @Param("status") String status);
}
