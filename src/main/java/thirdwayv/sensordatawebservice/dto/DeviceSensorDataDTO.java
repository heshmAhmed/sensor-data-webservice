package thirdwayv.sensordatawebservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DeviceSensorDataDTO {
    private String deviceId;
    private String temperature;
    private Date timestamp;
}
