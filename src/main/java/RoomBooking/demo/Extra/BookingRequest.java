package RoomBooking.demo.Extra;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    @NotNull(message = "roomId must not be null")
    private Long roomId;

    @NotBlank(message = "customerName must not be null")
    private String customerName;

    @NotNull(message = "checkIn must not be null")
    @FutureOrPresent(message = "Check‑in must be now or in the future")
    private LocalDate checkIn;

    @NotNull(message = "checkOut must not be null")
    @FutureOrPresent(message = "Check‑in must be now or in the future")
    private LocalDate checkOut;
}