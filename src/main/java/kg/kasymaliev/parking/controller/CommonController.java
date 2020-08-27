package kg.kasymaliev.parking.controller;

import kg.kasymaliev.parking.entity.Place;
import kg.kasymaliev.parking.entity.Tickets;
import kg.kasymaliev.parking.service.PlaceService;
import kg.kasymaliev.parking.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CommonController {
    private final PlaceService placeService;
    private final TicketService ticketService;

    @Autowired
    public CommonController(PlaceService placeService, TicketService ticketService) {
        this.placeService = placeService;
        this.ticketService = ticketService;
    }

    @GetMapping("/check/car/{carName}")
    public ResponseEntity<?> checkCar (@PathVariable String carName) {
        try {
            Tickets t = ticketService.getTicketByCar(carName);
            if(t==null)
                return new ResponseEntity<>("There is not car " + carName + " in the parking", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(t, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/parking/places/free")
    public ResponseEntity<?> getFreePlaces(){
        try{
            List<Place> placeList = placeService.getFreePlaces();
            if(placeList.size()==0)
                return new ResponseEntity<>("There is not any free place", HttpStatus.OK);

            return new ResponseEntity<>(placeList, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/parking/occupy/{id}")
    public ResponseEntity<?> occupyPlace(@PathVariable long id, @RequestParam String car) {
        try {
            if(placeService.getFreePlaces().size()==0)
                return new ResponseEntity<>("There is not any free place", HttpStatus.FORBIDDEN);

            if(!placeService.checkPlace(id))
                return new ResponseEntity<>("This place is occupied", HttpStatus.FORBIDDEN);

            Place place = placeService.changePlaceStatus(id, false);
            Tickets t = ticketService.newTicket(place, car);

            log.debug("New Ticket№ " + t.getId() + "; place " + place.getPlaceName() + " has been occupied");
            return new ResponseEntity<>(t, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/parking/release/{ticketId}")
    public ResponseEntity<?> releasePlace(@PathVariable long ticketId) {
        try {
            Tickets t = ticketService.closeTicket(ticketId);
            if(t==null)
                return new ResponseEntity<>("This ticket is closed", HttpStatus.NOT_FOUND);

            Place place = t.getPlace();

            placeService.changePlaceStatus(place.getPlaceId(), true);

            log.debug("Ticket№" + t.getId() + "; fee: " + t.getFee());
            log.debug("Place " + place.getPlaceName() + " has been released");
            return new ResponseEntity<>(t, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
