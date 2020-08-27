package kg.kasymaliev.parking.repository;

import kg.kasymaliev.parking.entity.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Long> {
    @Query("select t from Tickets  t where t.car=?1 and t.endTime is null")
    Tickets getTicketsByCar(String car);
}
