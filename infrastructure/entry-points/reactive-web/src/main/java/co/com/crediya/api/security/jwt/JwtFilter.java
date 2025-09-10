package co.com.crediya.api.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        // Lista de patrones permitidos sin autenticación
        List<String> publicPaths = List.of(
                "/api/v1/login",
                "/api-docs",
                "/v3/api-docs",
                "/webjars/",
                "/swagger-ui/",
                "/swagger-resources",
                "/configuration/"
        );

        // Verificar si el path actual es público
        boolean isPublicPath = publicPaths.stream()
                .anyMatch(path::contains);

        if (isPublicPath) {
            return chain.filter(exchange);
        }

        // Resto de la lógica de autenticación...
        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(auth == null) return Mono.error(new Throwable("Token no encontrado"));
        if(!auth.startsWith("Bearer ")) return Mono.error(new Throwable("Auth Invalido"));

        String token = auth.replace("Bearer ", "");
        exchange.getAttributes().put("token", token);
        return chain.filter(exchange);
    }
}
