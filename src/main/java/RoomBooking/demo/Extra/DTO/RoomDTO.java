package RoomBooking.demo.Extra.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {

    private Long id;

    @NotBlank(message = "roomNumber must not be null")
    private String roomNumber;

    @NotNull(message = "capacity must not be null")
    @Positive(message = "capacity must be greater than zero")
    private int capacity;

    @NotNull(message = "pricePerNight must not be null")
    private BigDecimal pricePerNight;

    private boolean available = true;
}
