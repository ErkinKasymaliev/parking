package kg.kasymaliev.parking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parking_tickets_seq")
    @SequenceGenerator(name = "parking_tickets_seq", sequenceName = "parking_tickets_seq", allocationSize = 1)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "car")
    private String car;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "fee")
    private double fee;
}
