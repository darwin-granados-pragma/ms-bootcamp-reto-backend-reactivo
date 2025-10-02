package co.com.bootcamp.consumer.rest;

import co.com.bootcamp.consumer.model.ErrorResponse;
import co.com.bootcamp.model.error.ErrorCode;
import co.com.bootcamp.model.exception.BusinessException;
import co.com.bootcamp.model.gateways.ReportGateway;
import co.com.bootcamp.model.report.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ReportRestConsumer implements ReportGateway {

  private final WebClient client;

  public ReportRestConsumer(@Qualifier("reportWebClient") WebClient client) {
    this.client = client;
  }

  @Override
  public Mono<Void> saveReport(Report data) {
    return client
        .post()
        .uri("/api/v1/reporte")
        .bodyValue(data)
        .retrieve()
        .onStatus(HttpStatusCode::isError,
            clientResponse -> clientResponse
                .bodyToMono(ErrorResponse.class)
                .flatMap(errorBody -> Mono.error(new BusinessException(ErrorCode.CANNOT_POSIBLE_SAVE_REPORT)))
        )
        .bodyToMono(Void.class);
  }
}
