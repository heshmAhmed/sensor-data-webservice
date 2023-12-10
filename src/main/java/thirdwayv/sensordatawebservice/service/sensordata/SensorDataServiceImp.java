package thirdwayv.sensordatawebservice.service.sensordata;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thirdwayv.sensordatawebservice.dto.DeviceSensorDataDTO;
import thirdwayv.sensordatawebservice.persistence.entity.SensorDataEntity;
import thirdwayv.sensordatawebservice.persistence.repository.SensorDataRepository;
import thirdwayv.sensordatawebservice.service.sensordatasse.SensorDataSSEService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SensorDataServiceImp implements SensorDataService {
    @Autowired
    private SensorDataRepository sensorDataRepository;
    @Autowired
    private SensorDataSSEService sensorDataSSEService;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void processAndStoreDevicesSensorsData(String  hexData) {
        log.info("processing hex data {}", hexData);
        List<SensorDataEntity> sensorDataEntities = parseHexDataToSensorDataEntities(hexData);
        sensorDataRepository.saveAll(sensorDataEntities);
        sendSensorDataSSEEvent(sensorDataRepository.findLatestSensorsData(Pageable.ofSize(4)));
        log.info("Sensor data processed and stored successfully.");
    }

    private void sendSensorDataSSEEvent(Page<SensorDataEntity> latestSensorsData) {
        sensorDataSSEService.sendSensorsDataToSSEClients(latestSensorsData
                .map(sensorDataEntity -> modelMapper.map(sensorDataEntity, DeviceSensorDataDTO.class)));
    }

    @Override
    public Page<DeviceSensorDataDTO> getLatestDevicesSensorsData(Pageable pageable) {
        return sensorDataRepository.findLatestSensorsData(pageable)
                .map(sensorDataEntity -> modelMapper.map(sensorDataEntity, DeviceSensorDataDTO.class));
    }

    @Override
    public Page<DeviceSensorDataDTO> getDeviceSensorData(String deviceId, Pageable pageable) {
        Page<SensorDataEntity> sensorDataEntityPage = sensorDataRepository.findByDeviceIdOrderByTimestampDesc(deviceId, pageable);
       return sensorDataEntityPage.map(sensorDataEntity -> modelMapper.map(sensorDataEntity, DeviceSensorDataDTO.class));
    }

    private List<SensorDataEntity> parseHexDataToSensorDataEntities(String hexData) {
        List<SensorDataEntity> sensorDataEntities = new ArrayList<>();
        int numOfSensorData = ((hexData.length() - 2) / 10);
        for (int i = 2; i < hexData.length() && numOfSensorData > 0; i += 10, numOfSensorData--) {
            long deviceId = Long.parseLong(hexData.substring(i, i + 8), 16);
            long temperature = Long.parseLong(hexData.substring(i + 8, i + 10), 16);
            sensorDataEntities.add(createSensorDataEntity(String.valueOf(deviceId), String.valueOf(temperature)));
        }
        return sensorDataEntities;
    }

    private SensorDataEntity createSensorDataEntity(String deviceId, String temperature){
        SensorDataEntity sensorDataEntity = new SensorDataEntity();
        sensorDataEntity.setDeviceId(deviceId);
        sensorDataEntity.setTemperature(temperature);
        sensorDataEntity.setTimestamp(new Date());
        return sensorDataEntity;
    }
}
