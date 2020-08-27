package kg.kasymaliev.parking.repository;

import kg.kasymaliev.parking.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findAllByIsfreeTrue();
    Place findByIsfreeTrueAndPlaceId(long id);
}
