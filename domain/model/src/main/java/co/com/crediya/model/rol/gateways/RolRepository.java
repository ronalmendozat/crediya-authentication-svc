package co.com.crediya.model.rol.gateways;

import co.com.crediya.model.rol.Rol;
import co.com.crediya.model.user.User;
import reactor.core.publisher.Mono;

public interface RolRepository {
    Mono<Rol> findById(Long id);
}
