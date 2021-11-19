package isu.library.model.repository;

import isu.library.model.entity.library.LibraryReservation;
import org.springframework.data.repository.CrudRepository;

public interface LibraryReservationRepository extends CrudRepository<LibraryReservation, Integer> {
    Iterable<LibraryReservation> findAllByBookLibraryId(Integer libraryId);
}
