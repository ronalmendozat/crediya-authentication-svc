package co.com.crediya.api;

import co.com.crediya.api.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    beanClass = Handler.class,
                    beanMethod = "saveUser",
                    operation = @Operation(
                            operationId = "saveUser",
                            summary = "Registrar un nuevo usuario",
                            description = "Recibe un objeto UserDTO y guarda un usuario en el sistema",
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Datos del usuario a registrar",
                                    content = @Content(schema = @Schema(implementation = UserDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Usuario guardado correctamente",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = String.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios/{document}",
                    beanClass = Handler.class,
                    beanMethod = "existUserByIdentityDocument",
                    operation = @Operation(
                            operationId = "existUserByIdentityDocument",
                            summary = "Validar existencia de un usuario",
                            description = "Verifica si un usuario existe en el sistema según su documento de identidad",
                            parameters = {
                                    @io.swagger.v3.oas.annotations.Parameter(
                                            name = "document",
                                            description = "Documento de identidad del usuario",
                                            required = true,
                                            schema = @Schema(type = "string")
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "El usuario existe o no existe",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = Boolean.class))
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/usuarios"), handler::saveUser)
                .andRoute(GET("/api/v1/usuarios/{document}"), handler::existUserByIdentityDocument);
    }
}
