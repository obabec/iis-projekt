package isu.library.model.repository;

import isu.library.model.entity.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("reservationRepository")
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    void deleteByBookId(Integer id);
}
