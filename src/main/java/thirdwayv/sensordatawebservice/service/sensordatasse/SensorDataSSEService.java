package thirdwayv.sensordatawebservice.service.sensordatasse;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import thirdwayv.sensordatawebservice.dto.DeviceSensorDataDTO;

import java.util.List;

public interface SensorDataSSEService {
    SseEmitter subscribeToSensorDataEvent();

    void sendSensorsDataEvent(Page<DeviceSensorDataDTO> sensorDataDTOPage);
}
