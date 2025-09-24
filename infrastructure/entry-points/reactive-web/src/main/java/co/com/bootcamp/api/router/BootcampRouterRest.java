package co.com.bootcamp.api.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import co.com.bootcamp.api.error.ErrorResponse;
import co.com.bootcamp.api.error.GlobalErrorWebFilter;
import co.com.bootcamp.api.handler.BootcampHandler;
import co.com.bootcamp.api.model.request.BootcampCreateRequest;
import co.com.bootcamp.api.model.response.BootcampResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class BootcampRouterRest {

  private static final String PATH = "/api/v1/bootcamp";

  private final BootcampHandler bootcampHandler;
  private final GlobalErrorWebFilter globalErrorWebFilter;

  @Bean
  @RouterOperation(method = RequestMethod.POST,
      path = PATH,
      beanClass = BootcampHandler.class,
      beanMethod = "createBootcamp",
      operation = @Operation(operationId = "createBootcamp",
          summary = "Crear bootcamp",
          description = "Recibe datos del bootcamp y devuelve el objeto creado",
          requestBody = @RequestBody(required = true,
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = BootcampCreateRequest.class)
              )
          ),
          responses = {@ApiResponse(responseCode = "200",
              description = "Bootcamp creado correctamente",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = BootcampResponse.class)
              )
          ), @ApiResponse(responseCode = "400",
              description = "Parámetros inválidos o faltantes",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          )}
      )
  )
  public RouterFunction<ServerResponse> routerFunction() {
    return route(POST(PATH), bootcampHandler::createBootcamp).filter(globalErrorWebFilter);
  }
}
