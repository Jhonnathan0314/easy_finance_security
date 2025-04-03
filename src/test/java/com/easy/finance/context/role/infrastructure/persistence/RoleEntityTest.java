package com.easy.finance.context.role.infrastructure.persistence;

import com.easy.finance.utils.constants.StateEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleEntityTest {

    @InjectMocks
    private RoleEntity roleEntity;

    @Test
    void onCreateTest() {
        roleEntity.onCreate();

        assertEquals(StateEnum.active, roleEntity.getState());
    }

}