package co.com.crediya.r2dbc;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        Long,
        UserReactiveRepository
        > implements UserRepository {
    private final TransactionalOperator transactionalOperator;
    public UserRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        super(repository, mapper, entity -> mapper.map(entity, User.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<User> save (User user){
        return super.save(user).as(transactionalOperator::transactional);
    }

    @Override
    public Mono<User> findByEmail(String email) {

        Mono<UserEntity> econtrado11 = repository.findByEmail(email);

        Mono<User> encontrado = repository.findByEmail(email).map(usuarioEntity -> mapper.map(usuarioEntity, User.class));

        return encontrado;
    }
}
