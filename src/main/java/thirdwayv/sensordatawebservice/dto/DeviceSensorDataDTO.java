package thirdwayv.sensordatawebservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceSensorDataDTO {
    private String deviceId;
    private String temperature;
    private Date timestamp;
}
