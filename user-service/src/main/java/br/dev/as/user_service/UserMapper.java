package br.dev.as.user_service;

import br.dev.as.user_service.dto.CreateUserRequestDTO;
import br.dev.as.user_service.dto.UserDTO;

public class UserMapper {

    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static User toEntity(final CreateUserRequestDTO dto) {
        return new User(dto.getName(), dto.getEmail());
    }

    public static UserDTO toDTO(final User entity) {
        return new UserDTO().id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail());
    }
}