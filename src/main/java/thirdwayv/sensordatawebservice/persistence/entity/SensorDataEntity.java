package thirdwayv.sensordatawebservice.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "sensor_data")
@ToString
@NoArgsConstructor
@Setter
@Getter
public class SensorDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "temperature")
    private String temperature;
    @Column(name = "timestamp")
    private Date timestamp;
}
