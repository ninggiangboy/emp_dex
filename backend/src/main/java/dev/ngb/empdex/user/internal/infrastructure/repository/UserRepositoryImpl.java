package dev.ngb.empdex.user.internal.infrastructure.repository;

import dev.ngb.empdex.shared.core.util.StringUtils;
import dev.ngb.empdex.user.internal.domain.model.User;
import dev.ngb.empdex.user.internal.domain.repository.UserRepository;
import dev.ngb.empdex.user.internal.infrastructure.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJdbcRepository userJdbcRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findById(UUID id) {
        return userJdbcRepository.findById(id).map(userEntityMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return userJdbcRepository.findAll().stream().map(userEntityMapper::toDomain).toList();
    }

    @Override
    public User save(User aggregate) {
        var userEntity = userEntityMapper.toEntity(aggregate);
        userEntity = userJdbcRepository.save(userEntity);
        return userEntityMapper.toDomain(userEntity);
    }

    @Override
    public void delete(User aggregate) {
        var userEntity = userEntityMapper.toEntity(aggregate);
        userJdbcRepository.softDeleteById(userEntity.id());
    }

    @Override
    public void deleteById(UUID id) {
        userJdbcRepository.softDeleteById(id);
    }

    @Override
    public boolean existsByUsernameOrEmail(String username, String email) {
        if (StringUtils.isNullOrBlank(username) || StringUtils.isNullOrBlank(email)) {
            throw new IllegalArgumentException("Username and email must not be null");
        }
        return userJdbcRepository.existsByUsernameOrEmail(username, email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (StringUtils.isNullOrBlank(email)) {
            throw new IllegalArgumentException("Email must not be null");
        }
        var userEntity = userJdbcRepository.findByEmail(email);
        return userEntity.map(userEntityMapper::toDomain);
    }
}
