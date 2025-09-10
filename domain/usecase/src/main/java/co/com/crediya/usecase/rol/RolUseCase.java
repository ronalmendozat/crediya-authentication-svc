package co.com.crediya.usecase.rol;

import co.com.crediya.model.rol.Rol;
import co.com.crediya.model.rol.gateways.RolRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RolUseCase {

    private final RolRepository rolRepository;

    public Mono<Rol> findById(Long id){
        return this.rolRepository.findById(id);
    }
}
