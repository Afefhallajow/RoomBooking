package RoomBooking.demo.ServiceImpl;

import RoomBooking.demo.Entity.Booking;
import RoomBooking.demo.Entity.Room;
import RoomBooking.demo.ExceptionHandling.BusinessException;
import RoomBooking.demo.ExceptionHandling.NotFoundException;
import RoomBooking.demo.Extra.BookingRequest;
import RoomBooking.demo.Extra.BookingStatus;
import RoomBooking.demo.Extra.DTO.BookingDTO;
import RoomBooking.demo.Mapper.BookingMapper;
import RoomBooking.demo.Repository.BookingRepository;
import RoomBooking.demo.Repository.RoomRepository;
import RoomBooking.demo.Service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;

    private Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    @Override
    public BookingDTO createBooking(BookingRequest request) {
        if (request.getCheckIn().isBefore(LocalDate.now())) {
            throw new BusinessException("checkIn must be now or in the future");
        }
        if (!request.getCheckIn().isBefore(request.getCheckOut())) {
            throw new BusinessException("checkIn must be before checkOut");
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NotFoundException("Room is not found"));

        if (!room.isAvailable()) throw new BusinessException("Room is not available");

        // check overlaps
        if (bookingRepository.findOverlapping(
                room.getId(),
                request.getCheckIn(),
                request.getCheckOut())) {
            throw new BusinessException("Room already booked during that period");
        }

        Booking booking = Booking.builder()
                .customerName(request.getCustomerName())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .status(BookingStatus.CONFIRMED)
                .room(room)
                .build();

        room.setAvailable(false);
        booking = bookingRepository.save(booking);

        //simulate to sent email
        log.debug("""
        [Simulated Email - Booking Confirmation]
        To: {}
        Subject: Room Booking Confirmation
        ---
        Hello {},
        Your booking has been successfully confirmed!
        Booking Details:
        Room Number: {}
        Check-In Date: {}
        Check-Out Date: {}
        ----
        Thank you for choosing our booking service.
        Regards,
        RoomBooking Team
        """,
                request.getCustomerName(),
                request.getCustomerName(),
                room.getRoomNumber(),
                request.getCheckIn(),
                request.getCheckOut());

        return bookingMapper.toDTO(booking);
    }

    @Override
    public BookingDTO cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        if (BookingStatus.CANCELLED.equals(booking.getStatus())){
            throw new BusinessException("booking is already canceled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.getRoom().setAvailable(true);
        return bookingMapper.toDTO(bookingRepository.save(booking));
    }


    @Override
    public BookingDTO getBooking(Long id) {
        return bookingMapper.toDTO(bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found")));
    }
}