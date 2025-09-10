package co.com.crediya.api;

import co.com.crediya.api.dto.UserDTORequest;
import co.com.crediya.api.dto.UserDTOResponse;
import co.com.crediya.api.security.model.LogInDTO;
import co.com.crediya.api.security.model.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
                                    content = @Content(schema = @Schema(implementation = UserDTORequest.class))
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Usuario guardado correctamente",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = UserDTOResponse.class))
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
                                    @Parameter(
                                            name = "document",
                                            description = "Documento de identidad del usuario",
                                            required = true,
                                            schema = @Schema(type = "string")
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Usuario encontrado",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = UserDTOResponse.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios/email/{email}",
                    beanClass = Handler.class,
                    beanMethod = "findUserByDocument",
                    operation = @Operation(
                            operationId = "findUserByDocument",
                            summary = "Buscar Usuario por documento",
                            description = "Regresa un usuario buscado según su Email",
                            parameters = {
                                    @Parameter(
                                            name = "email",
                                            description = "Email del usuario",
                                            required = true,
                                            schema = @Schema(type = "string")
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Usuario encontrado",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = UserDTOResponse.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/login",
                    beanClass = Handler.class,
                    beanMethod = "login",
                    operation = @Operation(
                            operationId = "login",
                            summary = "Login",
                            description = "Regresa un token",
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Correo y contraseña",
                                    content = @Content(schema = @Schema(implementation = LogInDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Token generado",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = TokenDTO.class))
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/usuarios"), handler::saveUser)
                .andRoute(GET("/api/v1/usuarios/{document}"), handler::findUserByDocument)
                .andRoute(GET("/api/v1/usuarios/email/{email}"), handler::findUserByEmail)
                .andRoute(POST("/api/v1/login"), handler::authenticate);
    }


}
