package RoomBooking.demo.Repository;

import RoomBooking.demo.Entity.Room;
import RoomBooking.demo.Repository.Core.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends BaseRepository<Room> {
    List<Room> findByAvailable(boolean available);
}