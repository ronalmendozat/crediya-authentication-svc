package co.com.crediya.api;

import co.com.crediya.api.dto.UserDTORequest;
import co.com.crediya.api.mapper.UserDTORequestMapper;
import co.com.crediya.api.mapper.UserDTOResponseMapper;
import co.com.crediya.api.security.jwt.JwtProvider;
import co.com.crediya.api.security.model.LogInDTO;
import co.com.crediya.api.security.model.TokenDTO;
import co.com.crediya.usecase.rol.RolUseCase;
import co.com.crediya.usecase.user.UserUseCase;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final UserUseCase userUseCase;
    private final Validator validator;
    private final UserDTORequestMapper userDTORequestMapper;
    private final UserDTOResponseMapper userDTOResponseMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RolUseCase rolUseCase;


    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(UserDTORequest.class)
                .doOnNext(dto -> log.info("Usuario a crear {}", dto.toString()))
                .flatMap(this::validate)
                .map(dto -> {
                    String encodedPassword = passwordEncoder.encode(dto.getPassword());
                    dto.setPassword(encodedPassword);
                    return dto;
                })
                .flatMap(dto -> userUseCase.saveUser(userDTORequestMapper.toUser(dto)))
                .doOnNext(user -> log.info("Usuario creado con éxito, con el Id: {}", user.getId()))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userDTOResponseMapper.toUserDto(user)));
    }

    public Mono<ServerResponse> findUserByDocument(ServerRequest serverRequest) {
        String numDocument = serverRequest.pathVariable("document");

        return userUseCase.findUserByDocument(numDocument)
                .switchIfEmpty(Mono.error(new RuntimeException("El usuario no existe")))
                .doOnNext(user -> log.info("Usuario encontrado {}", user))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userDTOResponseMapper.toUserDto(user)));
    }

    public Mono<ServerResponse> findUserByEmail(ServerRequest serverRequest) {
        String numDocument = serverRequest.pathVariable("email");

        return userUseCase.findUserByEmail(numDocument)
                .switchIfEmpty(Mono.error(new RuntimeException("El usuario no existe")))
                .doOnNext(user -> log.info("Usuario encontrado {}", user))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userDTOResponseMapper.toUserDto(user)));
    }

    public Mono<ServerResponse> existUserByIdentityDocument(ServerRequest serverRequest) {
        String identityDocument = serverRequest.pathVariable("document");
        return userUseCase.existUserByIdentityDocument(identityDocument)
                .flatMap(exist -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(exist));
    }

    public Mono<ServerResponse> authenticate(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LogInDTO.class)
                .flatMap(login -> userUseCase.findUserByEmail(login.email())
                        .switchIfEmpty(Mono.error(new RuntimeException("El usuario ingresado no existe")))
                        .flatMap(user -> {

                            if (!passwordEncoder.matches(login.password(), user.getPassword())) {
                                log.error("Las credenciales del usuario son incorrectas");
                                return Mono.error(new RuntimeException("Credenciales Incorrectas"));
                            }
                            return rolUseCase.findById(user.getRolId())
                                    .switchIfEmpty(Mono.error(new RuntimeException("El usuario no tiene un rol asignado")))
                                    .map(role -> {
                                        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                                        var springUser = new User(user.getEmail(), user.getPassword(), authorities);
                                        return new TokenDTO(jwtProvider.generateToken(springUser));
                                    });
                        })

                )
                .flatMap(tokenDto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tokenDto)
                );
    }

    private <T> Mono<T> validate(T dto) {
        var violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            return Mono.error(new ConstraintViolationException(violations));
        }
        return Mono.just(dto);
    }


}
