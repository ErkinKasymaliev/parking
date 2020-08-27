package kg.kasymaliev.parking.base;

import java.util.List;

public interface BaseService<S,D> {
    List<S> findAll();
    S findById(D id);
    S create(S s);
    S edit(S s);
    boolean deleteById(D id);
}
