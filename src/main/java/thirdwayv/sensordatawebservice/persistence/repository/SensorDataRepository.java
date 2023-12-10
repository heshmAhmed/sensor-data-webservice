package thirdwayv.sensordatawebservice.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import thirdwayv.sensordatawebservice.dto.DeviceSensorDataDTO;
import thirdwayv.sensordatawebservice.persistence.entity.SensorDataEntity;

import java.util.List;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorDataEntity, Long> {
    Page<SensorDataEntity> findByDeviceIdOrderByTimestampDesc(String deviceId, Pageable pageable);

    @Query(value = "SELECT new thirdwayv.sensordatawebservice.dto.DeviceSensorDataDTO(deviceId, temperature, max(timestamp))" +
            " from SensorDataEntity sd Group by deviceId")
    Page<DeviceSensorDataDTO> findLatestSensorsData(Pageable pageable);
}
