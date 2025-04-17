package RoomBooking.demo.Extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {
    private HttpStatus status;
    private String message;

    public ApiError() {
    }

    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}