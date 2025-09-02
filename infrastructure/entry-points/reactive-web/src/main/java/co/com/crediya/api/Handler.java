package co.com.crediya.api;

import co.com.crediya.api.dto.UserDTO;
import co.com.crediya.api.mapper.UserDTOMapper;
import co.com.crediya.usecase.user.UserUseCase;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final UserUseCase userUseCase;
    private final Validator validator;
    private final UserDTOMapper userDTOMapper;

    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(UserDTO.class)
                .doOnNext(dto -> log.info("Usuario a crear {}", dto.toString()))
                .flatMap(this::validate)
                .flatMap(dto -> userUseCase.saveUser(userDTOMapper.toUser(dto)))
                .doOnNext( user -> log.info("Usuario creado con éxito, con el Id: " + user.getId()))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userDTOMapper.toUserDto(user)));
    }

    private <T> Mono<T> validate(T dto) {
        var violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            return Mono.error(new ConstraintViolationException(violations));
        }
        return Mono.just(dto);
    }
}
