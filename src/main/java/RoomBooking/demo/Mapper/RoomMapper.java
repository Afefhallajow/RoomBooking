package RoomBooking.demo.Mapper;

import RoomBooking.demo.Entity.Room;
import RoomBooking.demo.Extra.DTO.RoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "id", source = "id")
    RoomDTO toDTO(Room room);

    @Mapping(target = "id", source = "id")
    Room toEntity(RoomDTO dto);
}
