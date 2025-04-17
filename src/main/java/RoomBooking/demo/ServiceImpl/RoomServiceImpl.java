package RoomBooking.demo.ServiceImpl;

import RoomBooking.demo.Entity.Room;
import RoomBooking.demo.Extra.DTO.RoomDTO;
import RoomBooking.demo.Mapper.RoomMapper;
import RoomBooking.demo.Repository.RoomRepository;
import RoomBooking.demo.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Override
    public List<RoomDTO> getAvailableRooms(Boolean available) {
        return roomRepository.findByAvailable(available)
                .stream()
                .map(roomMapper::toDTO)
                .toList();
    }

    @Override
    public RoomDTO addRoom(RoomDTO roomDTO) {
       logger.info("Input DTO: " + roomDTO);
        Room room = roomMapper.toEntity(roomDTO);
        Room saved = roomRepository.save(room);
        logger.info("Saved Entity: " + room);

        return roomMapper.toDTO(saved);
    }
    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .map(roomMapper::toDTO)
                .toList();
    }
}