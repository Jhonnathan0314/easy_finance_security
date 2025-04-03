package com.easy.finance.context.user.infrastructure.adapter;

import com.easy.finance.context.user.data.UserData;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.infrastructure.mappers.UserMapper;
import com.easy.finance.context.user.infrastructure.persistence.UserEntity;
import com.easy.finance.context.user.infrastructure.persistence.UserJpaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {

    @InjectMocks
    private UserJpaAdapter userRepositoryJpaAdapter;

    @Mock
    private UserJpaRepository userJpaRepository;

    private static UserMapper userMapper;
    private static UserData userData;

    @BeforeAll
    static void setUp() {
        userData = new UserData();
        userMapper = new UserMapper();
    }

    @Test
    @Order(0)
    void findAllUserTest() {
        List<UserEntity> mockEntities = userMapper.modelsToEntities(userData.getUsersList());
        when(userJpaRepository.findAll()).thenReturn(mockEntities);

        List<User> response = userRepositoryJpaAdapter.findAll();

        assertNotNull(response);
        assertEquals(mockEntities.size(), response.size());

        verify(userJpaRepository).findAll();
    }

    @Test
    @Order(1)
    void findByIdUserTest() {
        UserEntity mockEntity = userMapper.modelToEntity(userData.getUserResponseOne());
        when(userJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(mockEntity));

        User response = userRepositoryJpaAdapter.findById(1L).orElse(null);

        assertNotNull(response);
        assertEquals(response, userData.getUserResponseOne());

        verify(userJpaRepository).findById(any(Long.class));
    }

    @Test
    @Order(2)
    void findByEmailUserTest() {
        UserEntity mockEntity = userMapper.modelToEntity(userData.getUserResponseOne());
        when(userJpaRepository.findByEmail(any(String.class))).thenReturn(Optional.of(mockEntity));

        User response = userRepositoryJpaAdapter.findByEmail("test").orElse(null);

        assertNotNull(response);
        assertEquals(response, userData.getUserResponseOne());

        verify(userJpaRepository).findByEmail(any(String.class));
    }

    @Test
    @Order(3)
    void createUserTest() {
        UserEntity mockEntity = userMapper.modelToEntity(userData.getUserResponseOne());
        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(mockEntity);

        User response = userRepositoryJpaAdapter.create(userData.getUserCreateValid());

        assertNotNull(response);
        assertEquals(response, userData.getUserResponseOne());

        verify(userJpaRepository).save(any(UserEntity.class));
    }

    @Test
    @Order(4)
    void updateUserTest() {
        UserEntity mockEntity = userMapper.modelToEntity(userData.getUserResponseOne());
        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(mockEntity);

        User response = userRepositoryJpaAdapter.update(userData.getUserActive());

        assertNotNull(response);
        assertEquals(response, userData.getUserResponseOne());

        verify(userJpaRepository).save(any(UserEntity.class));
    }

    @Test
    @Order(5)
    void deleteByIdUserTest() {
        userRepositoryJpaAdapter.deleteById(1L);

        verify(userJpaRepository).deleteById(any(Long.class));
    }

}