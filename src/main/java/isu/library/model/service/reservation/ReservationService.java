/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.service.reservation;

import isu.library.model.entity.library.LibraryReservation;
import isu.library.model.entity.reservation.Reservation;
import isu.library.model.entity.reservation.UserReservation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public interface ReservationService {
    Iterable<Reservation> findAll();

    Iterable<LibraryReservation> findAllReservationsInLibrary(Integer libraryId);

    void deleteReservation(Integer reservationId);

    void switchToBorrow(Integer reservationId);

    Iterable<UserReservation> findAllUserReservations(Integer personId);

    Optional<Reservation> findReservationByBookIdAndPersonId(Integer bookId, Integer personId);

    void saveNewReservation(Integer bookId, Integer personId, LocalDate dateFrom);

    int saveNewLoan(Integer bookId, Integer personId, LocalDate dateFrom);

    Optional<Reservation> findReservationByLatestDate(Integer bookId);

    Iterable<Reservation> findReservationsByBookIdAndDateFromGreaterThan(Integer bookId, Date startDate);

    Optional<Reservation> findReservationById(Integer id);

    void updateReservation(Reservation reservation);

    void deleteByBookId(int bookId);
}
