package it.tsm.wiam.globalcontroller;

import it.tsm.wiam.reportistica.model.ReportisticaRequest;
import it.tsm.wiam.reportistica.model.ReportisticaResponse;
import it.tsm.wiam.reportistica.service.ReportisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/report")
public class ReportisticaController {


    private final ReportisticaService reportisticaService;


    @PostMapping("creareport")
    public ResponseEntity<ReportisticaResponse> creaReportistica(@RequestBody ReportisticaRequest request){
        return ResponseEntity.ok(reportisticaService.reportDettaglio(request));
    }

    // crea report mensile acquisti

    // crea report mensile vendite
}
