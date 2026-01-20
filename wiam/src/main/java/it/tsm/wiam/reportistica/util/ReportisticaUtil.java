package it.tsm.wiam.reportistica.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class ReportisticaUtil {

    ObjectMapper mapper = new ObjectMapper();

    public Object mappingEntityToDTO(Object o, Class<?> c){
        try{
            var dto = mapper.convertValue(o,c);
            return dto;
        }catch (Exception e){
            log.error("Error on mappingEntityToDTO with error: {}",e.getMessage());
            throw e;
        }
    }
}
