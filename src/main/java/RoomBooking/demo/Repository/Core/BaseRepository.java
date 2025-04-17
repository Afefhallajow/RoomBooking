package RoomBooking.demo.Repository.Core;

import RoomBooking.demo.Entity.Core.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}
