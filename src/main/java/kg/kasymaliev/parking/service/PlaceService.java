package kg.kasymaliev.parking.service;

import kg.kasymaliev.parking.base.BaseService;
import kg.kasymaliev.parking.entity.Place;

import java.util.List;

public interface PlaceService extends BaseService<Place, Long> {
    List<Place> getFreePlaces();
    Place changePlaceStatus(long id, boolean action);
    boolean checkPlace(long id);
}
