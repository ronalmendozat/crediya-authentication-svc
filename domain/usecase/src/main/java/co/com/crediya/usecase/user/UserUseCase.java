package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> saveUser (User user) {
        return userRepository.findByEmail(user.getEmail())
                // Si lo encuentra, lanzamos error (ya existe)
                .flatMap(existing -> Mono.<User>error(
                        new IllegalStateException("El usuario ya existe: " + existing.getEmail())))
                // Si no existe, guardamos
                .switchIfEmpty(Mono.defer(() -> userRepository.save(user)));
    }

    public Mono<User> getUsuarioByEmail(String email){
        return this.userRepository.findByEmail(email);
    }
}
