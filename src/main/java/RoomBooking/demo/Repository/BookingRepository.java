package RoomBooking.demo.Repository;

import RoomBooking.demo.Entity.Booking;
import RoomBooking.demo.Repository.Core.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BookingRepository extends BaseRepository<Booking> {

    @Query("SELECT (count (b) > 0) FROM Booking b " +
            "WHERE b.room.id = ?1 AND " +
            "b.status = 'CONFIRMED' AND " +
            "b.checkIn <  ?3 AND b.checkOut > ?2")
    Boolean findOverlapping(
            Long roomId,
            LocalDate newCheckIn,
            LocalDate newCheckOut);
}
