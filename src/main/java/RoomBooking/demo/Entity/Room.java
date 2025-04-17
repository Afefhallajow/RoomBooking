package RoomBooking.demo.Entity;

import RoomBooking.demo.Entity.Core.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room extends BaseEntity {

    @Column
    private String roomNumber;

    @Column
    private int capacity;

    @Column
    private BigDecimal pricePerNight;
    
    @Column
    private boolean available = true;
}