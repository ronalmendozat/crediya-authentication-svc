package co.com.crediya.r2dbc.repository.adapter;

import co.com.crediya.model.rol.Rol;
import co.com.crediya.model.rol.gateways.RolRepository;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.r2dbc.entity.RolEntity;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.r2dbc.repository.RolReactiveRepository;
import co.com.crediya.r2dbc.repository.UserReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class RolRepositoryAdapter extends ReactiveAdapterOperations<
        Rol,
        RolEntity,
        Long,
        RolReactiveRepository
        > implements RolRepository{
    private final TransactionalOperator transactionalOperator;

    public RolRepositoryAdapter(RolReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        super(repository, mapper, entity -> mapper.map(entity, Rol.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Rol> findById(Long id){
    return super.findById(id);
    }

}
