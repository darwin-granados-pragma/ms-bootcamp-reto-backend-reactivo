package co.com.bootcamp.model.gateways;

import co.com.bootcamp.model.report.Report;
import reactor.core.publisher.Mono;

public interface ReportGateway {

  Mono<Void> saveReport(Report data);
}
