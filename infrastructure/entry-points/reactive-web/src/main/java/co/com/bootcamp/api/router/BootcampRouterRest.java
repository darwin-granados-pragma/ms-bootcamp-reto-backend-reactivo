package co.com.bootcamp.api.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import co.com.bootcamp.api.error.ErrorResponse;
import co.com.bootcamp.api.error.GlobalErrorWebFilter;
import co.com.bootcamp.api.handler.BootcampHandler;
import co.com.bootcamp.api.model.request.BootcampCreateRequest;
import co.com.bootcamp.api.model.response.BootcampResponse;
import co.com.bootcamp.model.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class BootcampRouterRest {

  private static final String PATH = "/api/v1/bootcamp";
  private static final String PATH_DELETE = PATH + "/{idBootcamp}";

  private final BootcampHandler bootcampHandler;
  private final GlobalErrorWebFilter globalErrorWebFilter;

  @Bean
  @RouterOperations({@RouterOperation(method = RequestMethod.POST,
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
  ), @RouterOperation(method = RequestMethod.GET,
      path = PATH,
      beanClass = BootcampHandler.class,
      beanMethod = "findAll",
      operation = @Operation(operationId = "findAll",
          summary = "Obtener bootcamps y capacidades asociadas",
          description = "Recibe datos para parametrizar la búsqueda",
          parameters = {@Parameter(name = "sortBy",
              in = ParameterIn.QUERY,
              description = "Propiedad a ordenar",
              schema = @Schema(type = "String")
          ), @Parameter(name = "sortDirection",
              in = ParameterIn.QUERY,
              description = "Dirección de la lista (asc, desc)",
              schema = @Schema(type = "String")
          ), @Parameter(name = "page",
              in = ParameterIn.QUERY,
              description = "Número de pagina a recuperar",
              schema = @Schema(type = "Integer")
          ), @Parameter(name = "size",
              in = ParameterIn.QUERY,
              description = "Cantidad de registros por pagina",
              schema = @Schema(type = "Integer")
          )},
          responses = {@ApiResponse(responseCode = "200",
              description = "Lista de bootcamps recuperada",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = PageResponse.class)
              )
          )}
      )
  ), @RouterOperation(method = RequestMethod.DELETE,
      path = PATH_DELETE,
      beanClass = BootcampHandler.class,
      beanMethod = "deleteBootcamp",
      operation = @Operation(operationId = "deleteBootcamp",
          summary = "Eliminar bootcamp por id",
          description = "Recibe el identificador, elimina el bootcamp con las capacidades asociadas",
          parameters = {@Parameter(in = ParameterIn.PATH,
              description = "Identificador del bootcamp",
              schema = @Schema(type = "String")
          )},
          responses = {@ApiResponse(responseCode = "204",
              description = "Bootcamp y capacidades asociadas eliminadas correctamente"
          )}
      )
  )}
  )
  public RouterFunction<ServerResponse> routerFunction() {
    return route(POST(PATH), bootcampHandler::createBootcamp)
        .andRoute(GET(PATH), bootcampHandler::findAll)
        .andRoute(DELETE(PATH_DELETE), bootcampHandler::deleteBootcamp)
        .filter(globalErrorWebFilter);
  }
}
