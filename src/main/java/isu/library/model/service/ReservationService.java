package isu.library.model.service;

import isu.library.model.entity.LibraryReservation;
import isu.library.model.entity.Reservation;
import isu.library.model.entity.UserReservation;

import java.sql.Date;

public interface ReservationService {
    Iterable<Reservation> findAll();
    void addNewReservation(Integer bookId, Integer personId, Date dateFrom, Date dateTo, boolean borrow);
    Iterable<LibraryReservation> findAllReservationsInLibrary(Integer libraryId);
    void deleteReservation(Integer reservationId);
    void switchToBorrow(Integer reservationId);
    Iterable<UserReservation> findAllUserReservations(Integer personId);
}
