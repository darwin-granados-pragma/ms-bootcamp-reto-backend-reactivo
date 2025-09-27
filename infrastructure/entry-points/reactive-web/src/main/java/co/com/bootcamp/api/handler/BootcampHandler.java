package co.com.bootcamp.api.handler;

import co.com.bootcamp.api.mapper.BootcampRestMapper;
import co.com.bootcamp.api.model.request.BootcampCreateRequest;
import co.com.bootcamp.model.bootcamp.BootcampCreate;
import co.com.bootcamp.model.bootcamp.BootcampSortBy;
import co.com.bootcamp.model.error.ErrorCode;
import co.com.bootcamp.model.exception.InvalidFormatParamException;
import co.com.bootcamp.model.page.BootcampPageCommand;
import co.com.bootcamp.model.page.SortDirection;
import co.com.bootcamp.usecase.bootcamp.BootcampUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootcampHandler {

  private final BootcampUseCase useCase;
  private final BootcampRestMapper mapper;
  private final RequestValidator requestValidator;

  public Mono<ServerResponse> createBootcamp(ServerRequest serverRequest) {
    log.info("Received request to create a bootcamp at path={} method={}",
        serverRequest.path(),
        serverRequest.method()
    );
    return serverRequest
        .bodyToMono(BootcampCreateRequest.class)
        .flatMap(request -> requestValidator
            .validate(request)
            .then(Mono.defer(() -> {
              BootcampCreate bootcampCreate = mapper.toBootcampCreate(request);
              return useCase
                  .createBootcamp(bootcampCreate)
                  .map(mapper::toBootcampResponse)
                  .flatMap(response -> ServerResponse
                      .status(HttpStatus.CREATED)
                      .contentType(MediaType.APPLICATION_JSON)
                      .bodyValue(response));
            })));
  }

  public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
    log.info("Request received for bootcamp list: path={}, method={}",
        serverRequest.path(),
        serverRequest.method()
    );
    return Mono
        .defer(() -> {
          try {
            String sortByParam = serverRequest
                .queryParam("sortBy")
                .orElse("name");
            String directionParam = serverRequest
                .queryParam("sortDirection")
                .orElse("ASC");
            int page = Integer.parseInt(serverRequest
                .queryParam("page")
                .orElse("0"));
            int size = Integer.parseInt(serverRequest
                .queryParam("size")
                .orElse("10"));

            BootcampPageCommand command = BootcampPageCommand
                .builder()
                .page(page)
                .size(size)
                .sortBy(BootcampSortBy.getSortBy(sortByParam))
                .sortDirection(SortDirection.getSortBy(directionParam))
                .build();

            return useCase.getBootcampResponses(command);

          } catch (IllegalArgumentException ex) {
            log.error("Invalid parameter format", ex);
            return Mono.error(new InvalidFormatParamException(ErrorCode.INVALID_PARAMETERS));
          }
        })
        .flatMap(pageResponse -> ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(pageResponse));
  }

  public Mono<ServerResponse> deleteBootcamp(ServerRequest serverRequest) {
    log.info("Received request to delete a bootcamp at path={} method={}",
        serverRequest.path(),
        serverRequest.method()
    );
    return Mono.defer(() -> {
      String idBootcamp = serverRequest.pathVariable("idBootcamp");
      return useCase
          .deleteBootcampAndRelationsWithCapacities(idBootcamp)
          .then(ServerResponse
              .noContent()
              .build());
    });
  }
}
