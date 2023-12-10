package thirdwayv.sensordatawebservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import thirdwayv.sensordatawebservice.dto.DeviceSensorDataDTO;
import thirdwayv.sensordatawebservice.controller.dto.InsertDevicesSensoreDataRequest;
import thirdwayv.sensordatawebservice.service.sensordatasse.SensorDataSSEService;
import thirdwayv.sensordatawebservice.service.sensordata.SensorDataService;

@RestController
@RequestMapping("/devices-sensors-data")
public class SensorDataController {
    @Autowired
    private SensorDataService sensorDataService;
    @Autowired
    private SensorDataSSEService sensorDataSSEService;

    @PostMapping
    public ResponseEntity insertDevicesSensorsData(@Valid @RequestBody InsertDevicesSensoreDataRequest hexData) {
        sensorDataService.processAndStoreDevicesSensorsData(hexData.getHexData());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<DeviceSensorDataDTO>> getLatestDevicesSensorsData(Pageable pageable) {
        return ResponseEntity.ok(sensorDataService.getLatestDevicesSensorsData(pageable));
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<Page<DeviceSensorDataDTO>> getDeviceSensorsData(@PathVariable String deviceId, Pageable pageable) {
        return ResponseEntity.ok(sensorDataService.getDeviceSensorData(deviceId, pageable));
    }


    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToSensorsDataStream() {
        return sensorDataSSEService.subscribeToSensorDataEvent();
    }
}
