package co.com.crediya.r2dbc.repository.adapter;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.r2dbc.repository.UserReactiveRepository;
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
    public Mono<User> save(User user) {
        return super.save(user).as(transactionalOperator::transactional);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .flatMap(userEntity -> Mono.just(mapper.map(userEntity, User.class)));
    }

    @Override
    public Mono<User> findByIdentityDocument(String identityDocument) {
        return repository.findByIdentityDocument(identityDocument)
                .flatMap(userEntity -> Mono.just(mapper.map(userEntity, User.class)));

    }
}
