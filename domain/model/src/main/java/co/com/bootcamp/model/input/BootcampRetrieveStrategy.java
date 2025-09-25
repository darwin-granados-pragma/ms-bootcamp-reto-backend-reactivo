package co.com.bootcamp.model.input;

import co.com.bootcamp.model.bootcamp.BootcampResponse;
import co.com.bootcamp.model.bootcamp.BootcampSortBy;
import co.com.bootcamp.model.page.BootcampPageCommand;
import co.com.bootcamp.model.page.PageResponse;
import reactor.core.publisher.Mono;

public interface BootcampRetrieveStrategy {

  BootcampSortBy getType();

  Mono<PageResponse<BootcampResponse>> getBootcampResponse(BootcampPageCommand command);
}
