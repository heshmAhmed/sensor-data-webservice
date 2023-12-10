package thirdwayv.sensordatawebservice.service.sensordatasse;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import thirdwayv.sensordatawebservice.dto.DeviceSensorDataDTO;

public interface SensorDataSSEService {
    SseEmitter subscribeToSensorDataEvent();

    void sendSensorsDataToSSEClients(Page<DeviceSensorDataDTO> sensorDataDTOPage);
}
