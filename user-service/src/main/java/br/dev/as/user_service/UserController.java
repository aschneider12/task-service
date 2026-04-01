package br.dev.as.user_service;

import br.dev.as.user_service.api.UsersApi;
import br.dev.as.user_service.dto.CreateUserRequestDTO;
import br.dev.as.user_service.dto.PaginatedResultDTO;
import br.dev.as.user_service.dto.UpdateUserRequestDTO;
import br.dev.as.user_service.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    @NonNull
    private final UserService userService;

    @Override
    public ResponseEntity<UserDTO> createUser(@Valid final CreateUserRequestDTO body) {
        return ResponseEntity.ok(userService.createUser(body));
    }

    @Override
    public ResponseEntity<Void>deleteUserById(final Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PaginatedResultDTO>getAllUsers(@Valid final Integer page,
                                                         @Valid final Integer size,
                                                         @Valid final String sort,
                                                         @Valid final String name,
                                                         @Valid final String email) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, sort, name, email));
    }

    @Override
    public ResponseEntity<UserDTO>getUserById(final Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Override
    public ResponseEntity<UserDTO>updateUserById(final Long userId,
                                                 @Valid final UpdateUserRequestDTO body) {
        return ResponseEntity.ok(userService.updateUserById(userId, body));
    }

}