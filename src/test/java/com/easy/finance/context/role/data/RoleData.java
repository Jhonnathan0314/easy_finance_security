package com.easy.finance.context.role.data;

import com.easy.finance.context.role.application.dto.RoleCreateDto;
import com.easy.finance.context.role.application.dto.RoleDto;
import com.easy.finance.context.role.application.dto.RoleUpdateDto;
import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.infrastructure.persistence.RoleEntity;
import com.easy.finance.utils.constants.StateEnum;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class RoleData {

    //Correct information
    private final Role roleActive = Role.builder()
            .id(1L)
            .name("test")
            .state(StateEnum.active)
            .build();

    private final Role roleInactive = Role.builder()
            .id(1L)
            .name("test")
            .state(StateEnum.inactive)
            .build();

    private final Role roleEmpty = Role.builder()
            .id(1L)
            .name("")
            .state(null)
            .build();

    private final Role roleCreateValid = Role.builder()
            .name("test")
            .build();

    private final Role roleCreateInvalid = Role.builder().build();

    private final Role roleToUpdate = Role.builder()
            .id(1L)
            .name("update")
            .state(StateEnum.active)
            .build();

    private final Role roleUpdated = Role.builder()
            .id(1L)
            .name("update")
            .state(StateEnum.active)
            .build();

    private final Role roleToUpdateNoId = Role.builder()
            .name("update")
            .state(StateEnum.active)
            .build();

    private final Role roleToUpdateInvalid = Role.builder()
            .id(1L)
            .state(StateEnum.active)
            .build();

    private final Role roleResponseOne = Role.builder()
            .id(1L)
            .name("test")
            .state(StateEnum.active)
            .build();

    private final Role roleResponseTwo = Role.builder()
            .id(2L)
            .name("test2")
            .state(StateEnum.active)
            .build();

    private final String encodedPassword = "12345ENCODED";

    private List<Role> rolesList;

    //To mapper test
    private final RoleEntity roleEntity = RoleEntity.builder()
            .id(1L)
            .name("test")
            .state(StateEnum.active)
            .build();

    private final RoleDto roleDTO = RoleDto.builder()
            .id(1L)
            .name("test")
            .state(StateEnum.active)
            .build();

    private final RoleCreateDto roleCreateDTO = RoleCreateDto.builder()
            .name("test")
            .build();

    private final RoleUpdateDto roleUpdateDTO = RoleUpdateDto.builder()
            .id(1L)
            .name("test")
            .state(StateEnum.active)
            .build();

    private final Role roleModel = Role.builder()
            .id(1L)
            .name("test")
            .state(StateEnum.active)
            .build();

    public RoleData() {
        rolesList = new LinkedList<>();
        rolesList.add(roleResponseOne);
        rolesList.add(roleResponseTwo);
    }

}
