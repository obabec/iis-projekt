/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.repository;

import isu.library.model.entity.reservation.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository("reservationRepository")
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    void deleteByBookId(Integer id);
    Iterable<Reservation> findReservationByDateFromBefore(Date lastResDate);

    Optional<Reservation> findReservationByBookIdAndPersonId(Integer bookId, Integer personId);

    @Query(value = "SELECT * FROM blocking r WHERE r.date_to = (SELECT MAX(r2.date_to) FROM blocking r2 WHERE r2.book_id = :book_id)", nativeQuery = true)
    Optional<Reservation> findReservationByLatestDate(@Param("book_id") Integer bookId);

    Iterable<Reservation> findReservationsByBookIdAndDateFromGreaterThan(Integer bookId, Date startDate);

    Optional<Reservation> findReservationById(Integer id);
}
