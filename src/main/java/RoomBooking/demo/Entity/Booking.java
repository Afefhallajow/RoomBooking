package RoomBooking.demo.Entity;

import RoomBooking.demo.Entity.Core.BaseEntity;
import RoomBooking.demo.Extra.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking  extends BaseEntity {

    private String customerName;
    private LocalDate checkIn;
    private LocalDate checkOut;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
