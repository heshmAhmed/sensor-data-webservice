package thirdwayv.sensordatawebservice.service.sensordata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thirdwayv.sensordatawebservice.dto.DeviceSensorDataDTO;

public interface SensorDataService {
    void processAndStoreDevicesSensorsData(String sensorsData);

    Page<DeviceSensorDataDTO> getDeviceSensorData(String deviceId, Pageable pageable);

    Page<DeviceSensorDataDTO> getLatestDevicesSensorsData(Pageable pageable);
}
