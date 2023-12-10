package thirdwayv.sensordatawebservice.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsertDevicesSensoreDataRequest {
    @NotBlank(message = "hexadecimal string is mandatory")
    @Pattern(regexp =  "0[xX][0-9a-fA-F]+", message = "Invalid hexadecimal")
    @Size(min = 12, message = "hexadecimal string must be at least 12 characters")
    private String hexData;
}
