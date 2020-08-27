package kg.kasymaliev.parking.service.impl;

import kg.kasymaliev.parking.entity.Place;
import kg.kasymaliev.parking.repository.PlaceRepository;
import kg.kasymaliev.parking.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository repository;

    @Autowired
    public PlaceServiceImpl(PlaceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Place> findAll() {
        return repository.findAll();
    }

    @Override
    public Place findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Place create(Place place) {
        repository.save(place);
        return place;
    }

    @Override
    public Place edit(Place place) {
        repository.saveAndFlush(place);
        return place;
    }

    @Override
    public boolean deleteById(Long id) {
        repository.deleteById(id);
        return false;
    }

    @Override
    public List<Place> getFreePlaces() {
        return repository.findAllByIsfreeTrue();
    }

    @Override
    public Place changePlaceStatus(long id, boolean action) {
        Place place = findById(id);
        place.setIsfree(action);
        return edit(place);
    }

    @Override
    public boolean checkPlace(long id) {
        return repository.findByIsfreeTrueAndPlaceId(id)!=null ? true : false;
    }
}
