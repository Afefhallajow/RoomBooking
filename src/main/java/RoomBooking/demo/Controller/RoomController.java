package RoomBooking.demo.Controller;

import RoomBooking.demo.Extra.DTO.RoomDTO;
import RoomBooking.demo.Service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getRooms(
            @RequestParam(name = "available", required = false) Boolean available) {

        List<RoomDTO> rooms = (available == null)
                ? roomService.getAllRooms()
                : roomService.getAvailableRooms(available);
        return ResponseEntity.ok(rooms);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO dto) {
        RoomDTO created = roomService.addRoom(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}