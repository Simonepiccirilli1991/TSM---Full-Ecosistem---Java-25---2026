package it.tsm.wiam.globalcontroller;

import it.tsm.wiam.reportistica.model.ReportisticaMensileRequest;
import it.tsm.wiam.reportistica.model.ReportisticaRequest;
import it.tsm.wiam.reportistica.model.ReportisticaResponse;
import it.tsm.wiam.reportistica.service.ReportisticaMensileService;
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
    private final ReportisticaMensileService reportisticaMensileService;


    @PostMapping("creareport")
    public ResponseEntity<ReportisticaResponse> creaReportistica(@RequestBody ReportisticaRequest request){
        return ResponseEntity.ok(reportisticaService.reportDettaglio(request));
    }

    // crea report mensile acquisti
    @PostMapping("creareportmensile/acquisti")
    public ResponseEntity<ReportisticaResponse> creaReportMensileAcquisti(@RequestBody ReportisticaMensileRequest request){
        return ResponseEntity.ok(reportisticaMensileService.reportMensileAcquisti(request));
    }
    // crea report mensile vendite
    @PostMapping("creareportmensile/vendite")
    public ResponseEntity<ReportisticaResponse> creaReportMensileVendite(@RequestBody ReportisticaMensileRequest request){
        return ResponseEntity.ok(reportisticaMensileService.reportMensileVendite(request));
    }
}
