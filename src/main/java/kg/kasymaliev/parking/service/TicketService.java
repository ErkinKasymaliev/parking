package kg.kasymaliev.parking.service;

import kg.kasymaliev.parking.base.BaseService;
import kg.kasymaliev.parking.entity.Place;
import kg.kasymaliev.parking.entity.Tickets;

public interface TicketService extends BaseService<Tickets, Long> {
    Tickets newTicket(Place place, String car);
    Tickets closeTicket(long id);
    Tickets getTicketByCar(String car);
}
