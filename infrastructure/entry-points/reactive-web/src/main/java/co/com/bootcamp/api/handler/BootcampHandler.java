package co.com.bootcamp.api.handler;

import co.com.bootcamp.api.mapper.BootcampRestMapper;
import co.com.bootcamp.api.model.request.BootcampCreateRequest;
import co.com.bootcamp.model.bootcamp.BootcampCreate;
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
}
