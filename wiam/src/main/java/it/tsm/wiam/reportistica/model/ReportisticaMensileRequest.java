package it.tsm.wiam.reportistica.model;

import java.time.LocalDateTime;

public record ReportisticaMensileRequest(String tipoProdotto, LocalDateTime startDate,LocalDateTime endDate) {
}
