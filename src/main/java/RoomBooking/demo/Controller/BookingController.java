package RoomBooking.demo.Controller;

import RoomBooking.demo.Extra.BookingRequest;
import RoomBooking.demo.Extra.DTO.BookingDTO;
import RoomBooking.demo.Service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(
            @Valid @RequestBody BookingRequest request) {
        BookingDTO booking = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingDTO> cancelBooking(@PathVariable Long id) {
        BookingDTO cancelled = bookingService.cancelBooking(id);
        return ResponseEntity.ok(cancelled);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }
}