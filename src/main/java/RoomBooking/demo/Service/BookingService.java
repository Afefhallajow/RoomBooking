package RoomBooking.demo.Service;

import RoomBooking.demo.Extra.BookingRequest;
import RoomBooking.demo.Extra.DTO.BookingDTO;

public interface BookingService {
    BookingDTO createBooking(BookingRequest request);

    BookingDTO cancelBooking(Long id);

    BookingDTO getBooking(Long id);
}
