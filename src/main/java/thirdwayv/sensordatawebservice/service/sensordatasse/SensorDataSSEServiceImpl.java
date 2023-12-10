package thirdwayv.sensordatawebservice.service.sensordatasse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import thirdwayv.sensordatawebservice.dto.DeviceSensorDataDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Slf4j
public class SensorDataSSEServiceImpl implements SensorDataSSEService {
    private Queue<SseEmitter> emitters = new ConcurrentLinkedQueue<>();
    @Override
    public SseEmitter subscribeToSensorDataEvent() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        this.emitters.add(sseEmitter);
        sseEmitter.onCompletion(()-> this.emitters.remove(sseEmitter));
        sseEmitter.onTimeout(()-> this.emitters.remove(sseEmitter));
        return sseEmitter;
    }

    @Override
    public void sendSensorsDataEvent(Page<DeviceSensorDataDTO> sensorDataDTOPage) {

        List<SseEmitter> deadEmitters = new ArrayList<>();
        this.emitters.forEach(emitter -> {
            try {
                log.info("Sending sensor data event to {}", emitter.toString());
                emitter.send(sensorDataDTOPage);
            } catch (Exception e) {
                log.error("Error sending sensor data event {}", e.getMessage());
                deadEmitters.add(emitter);
            }
        });
        this.emitters.removeAll(deadEmitters);
    }
}
