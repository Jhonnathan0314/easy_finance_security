package com.easy.finance.context.user.data;

import com.easy.finance.context.role.application.dto.RoleDto;
import com.easy.finance.context.role.application.dto.RoleUpdateDto;
import com.easy.finance.context.role.domain.model.Role;
import com.easy.finance.context.role.infrastructure.persistence.RoleEntity;
import com.easy.finance.context.user.application.dto.UserCreateDto;
import com.easy.finance.context.user.application.dto.UserDto;
import com.easy.finance.context.user.application.dto.UserResponseDto;
import com.easy.finance.context.user.application.dto.UserUpdateDto;
import com.easy.finance.context.user.domain.model.User;
import com.easy.finance.context.user.infrastructure.persistence.UserEntity;
import com.easy.finance.utils.constants.StateEnum;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class UserData {

    //Correct information
    private final User userActive = User.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .password("12345")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .state(StateEnum.active)
            .build();

    private final User userInactive = User.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .password("12345")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .state(StateEnum.inactive)
            .build();

    private final User userEmpty = User.builder()
            .id(1L)
            .name("")
            .lastName("")
            .email("")
            .password("")
            .state(null)
            .build();

    private final User userCreateValid = User.builder()
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .password("12345")
            .build();

    private final User userCreateNoPassword = User.builder()
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .build();

    private final User userCreateInvalid = User.builder()
            .name("test")
            .lastName("test")
            .password("12345")
            .build();

    private final User userToUpdate = User.builder()
            .id(1L)
            .name("update")
            .lastName("update")
            .email("test@test.com")
            .role(Role.builder()
                    .id(1L)
                    .build())
            .password("12345")
            .build();

    private final User userToUpdateChangeEmail = User.builder()
            .id(1L)
            .name("update")
            .lastName("update")
            .email("test2@test.com")
            .password("12345")
            .role(Role.builder()
                    .id(1L)
                    .build())
            .build();

    private final User userToUpdateNoId = User.builder()
            .name("update")
            .lastName("update")
            .email("test@test.com")
            .password("12345")
            .role(Role.builder()
                    .id(1L)
                    .build())
            .build();

    private final User userToUpdateNoChanges = User.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .role(Role.builder()
                    .id(1L)
                    .build())
            .state(StateEnum.active)
            .build();

    private final User userToUpdateNoPassword = User.builder()
            .id(1L)
            .name("update")
            .lastName("update")
            .email("test@test.com")
            .build();

    private final User userToUpdateInvalid = User.builder()
            .id(1L)
            .email("test@test.com")
            .password("12345")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .build();

    private final User userUpdated = User.builder()
            .id(1L)
            .name("update")
            .lastName("update")
            .email("test@test.com")
            .password("12345")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .state(StateEnum.active)
            .build();

    private final User userResponseOne = User.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .build();

    private final User userResponseTwo = User.builder()
            .id(2L)
            .name("test2")
            .lastName("test2")
            .email("test2@test.com")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .build();

    private final String encodedPassword = "12345ENCODED";

    private List<User> usersList;

    //To mapper test
    private final UserEntity userEntity = UserEntity.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .password("12345")
            .role(RoleEntity.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .state(StateEnum.active)
            .build();

    private final UserDto userDTO = UserDto.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .password("12345")
            .role(RoleDto.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .build();

    private final UserCreateDto userCreateDTO = UserCreateDto.builder()
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .password("12345")
            .build();

    private final UserUpdateDto userUpdateDTO = UserUpdateDto.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .password("12345")
            .role(RoleUpdateDto.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .build();

    private final UserResponseDto userResponseDTO = UserResponseDto.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .role(RoleDto.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .build();

    private final User userModel = User.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .email("test@test.com")
            .password("12345")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .state(StateEnum.active)
            .build();

    public UserData() {
        usersList = new LinkedList<>();
        usersList.add(userResponseOne);
        usersList.add(userResponseTwo);
    }
}
