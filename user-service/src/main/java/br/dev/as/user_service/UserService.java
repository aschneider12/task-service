package br.dev.as.user_service;

import br.dev.as.user_service.dto.CreateUserRequestDTO;
import br.dev.as.user_service.dto.PaginatedResultDTO;
import br.dev.as.user_service.dto.UpdateUserRequestDTO;
import br.dev.as.user_service.dto.UserDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    @NonNull
    private final UserRepository userRepository;

    public UserDTO createUser(final CreateUserRequestDTO body) {
        if (userRepository.existsByEmail(body.getEmail())) {
            throw new UserAlreadyExistsException(body.getEmail());
        }

        final var toCreate = UserMapper.toEntity(body);
        final var created = userRepository.save(toCreate);
        return UserMapper.toDTO(created);
    }

    public void deleteUserById(final Long userId) {
        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public PaginatedResultDTO getAllUsers(final Integer page,
                                          final Integer size,
                                          final String sort,
                                          final String name,
                                          final String email) {
        final var sortParams = sort.split(",");
        final var sortField = sortParams[0];
        final var sortDirection = sortParams[1];
        final var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        final var specification = UserSpecification.create(name, email);
        final var users = userRepository.findAll(specification, pageable);
        final var totalElements = users.getTotalElements();
        final var totalPages = users.getTotalPages();
        final var userDTOs = users.getContent().stream()
                .map(UserMapper::toDTO)
                .toList();
        return new PaginatedResultDTO()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .content(userDTOs);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(final Long userId) {
        return userRepository.findById(userId)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserDTO updateUserById(final Long userId,
                                  final UpdateUserRequestDTO body) {
        final var toUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        toUpdate.setName(body.getName());
        toUpdate.setEmail(body.getEmail());
        final var updated = userRepository.save(toUpdate);
        return UserMapper.toDTO(updated);
    }

}