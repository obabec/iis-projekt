package isu.library.model.service.reservation;

import isu.library.model.entity.library.LibraryReservation;
import isu.library.model.entity.reservation.Reservation;
import isu.library.model.entity.reservation.UserReservation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public interface ReservationService {
    Iterable<Reservation> findAll();
    void addNewReservation(Integer bookId, Integer personId, Date dateFrom, Date dateTo, boolean borrow);
    Iterable<LibraryReservation> findAllReservationsInLibrary(Integer libraryId);
    void deleteReservation(Integer reservationId);
    void switchToBorrow(Integer reservationId);
    Iterable<UserReservation> findAllUserReservations(Integer personId);
    Optional<Reservation> findReservationByBookIdAndPersonId(Integer bookId, Integer personId);
    void saveNewReservation(Integer bookId, Integer personId, LocalDate dateFrom);
    Optional<Reservation> findReservationByLatestDate(Integer bookId);
    Iterable<Reservation> findReservationsByBookIdAndDateFromGreaterThan(Integer bookId, Date startDate);
    Optional<Reservation> findReservationById(Integer id);
    void updateReservation(Reservation reservation);
}
