package com.easy.finance.context.user.infrastructure.adapter;

import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.domain.port.UserRepository;
import com.easy.finance.context.user.infrastructure.mappers.UserCreateMapper;
import com.easy.finance.context.user.infrastructure.mappers.UserMapper;
import com.easy.finance.context.user.infrastructure.mappers.UserUpdateMapper;
import com.easy.finance.context.user.infrastructure.persistence.UserEntity;
import com.easy.finance.context.user.infrastructure.persistence.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper = new UserMapper();
    private final UserCreateMapper userCreateMapper = new UserCreateMapper();
    private final UserUpdateMapper userUpdateMapper = new UserUpdateMapper();

    @Override
    public List<User> findAll() {
        return userMapper.entitiesToModels(userJpaRepository.findAll());
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> optUserEntity = userJpaRepository.findById(id);
        return optUserEntity.map(userMapper::entityToModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> optUserEntity = userJpaRepository.findByEmail(email);
        return optUserEntity.map(userMapper::entityToModel);
    }

    @Override
    public User create(User user) {
        UserEntity userEntity = userJpaRepository.save(userCreateMapper.modelToEntity(user));
        return userMapper.entityToModel(userEntity);
    }

    @Override
    public User update(User user) {
        UserEntity userEntity = userJpaRepository.save(userUpdateMapper.modelToEntity(user));
        return userMapper.entityToModel(userEntity);
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }
}
