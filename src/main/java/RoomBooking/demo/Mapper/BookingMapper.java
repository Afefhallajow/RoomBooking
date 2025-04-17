package RoomBooking.demo.Mapper;

import RoomBooking.demo.Entity.Booking;
import RoomBooking.demo.Extra.BookingRequest;
import RoomBooking.demo.Extra.DTO.BookingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = RoomMapper.class)
public interface BookingMapper {
    @Mapping(target = "id", source = "id")
    BookingDTO toDTO(Booking booking);
}