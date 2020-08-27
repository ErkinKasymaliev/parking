package kg.kasymaliev.parking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "parking_place")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    @Id
    @Column(name = "place_id")
    private long placeId;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "is_free")
    private boolean isfree;
}
