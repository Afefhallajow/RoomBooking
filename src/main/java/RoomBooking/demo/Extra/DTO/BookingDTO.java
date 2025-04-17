package RoomBooking.demo.Extra.DTO;

import RoomBooking.demo.Extra.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {
    private Long id;
    private String customerName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BookingStatus status;
    private RoomDTO room;
}