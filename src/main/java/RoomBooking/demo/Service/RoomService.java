package RoomBooking.demo.Service;

import RoomBooking.demo.Extra.DTO.RoomDTO;

import java.util.List;

public interface RoomService {
    List<RoomDTO> getAvailableRooms(Boolean available);
    RoomDTO addRoom(RoomDTO roomDTO);

    List<RoomDTO> getAllRooms();
}
