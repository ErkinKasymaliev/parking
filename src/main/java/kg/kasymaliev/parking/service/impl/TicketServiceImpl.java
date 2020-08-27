package kg.kasymaliev.parking.service.impl;

import kg.kasymaliev.parking.entity.Place;
import kg.kasymaliev.parking.entity.Tickets;
import kg.kasymaliev.parking.repository.TicketRepository;
import kg.kasymaliev.parking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository repository;
    private final long feePerSec = 2l; /*оплата за 1 сек*/

    @Autowired
    public TicketServiceImpl(TicketRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Tickets> findAll() {
        return repository.findAll();
    }

    @Override
    public Tickets findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Tickets create(Tickets tickets) {
        repository.save(tickets);
        return tickets;
    }

    @Override
    public Tickets edit(Tickets tickets) {
        repository.saveAndFlush(tickets);
        return tickets;
    }

    @Override
    public boolean deleteById(Long id) {
        repository.deleteById(id);
        return false;
    }

    @Override
    public Tickets newTicket(Place place, String car) {
        Tickets ticket = new Tickets();
        ticket.setPlace(place);
        ticket.setCar(car);
        ticket.setStartTime(LocalDateTime.now());
        repository.save(ticket);
        return ticket;
    }

    @Override
    public Tickets closeTicket(long id) {
        Tickets ticket = findById(id);

        if(ticket.getEndTime()!=null)
            return null;

        LocalDateTime endTime = LocalDateTime.now();
        ticket.setEndTime(endTime);
        ticket.setFee(Duration.between(ticket.getStartTime(), endTime).getSeconds()*feePerSec);
        return edit(ticket);

    }

    @Override
    public Tickets getTicketByCar(String car) {
        return repository.getTicketsByCar(car);
    }
}
