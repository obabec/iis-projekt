package isu.library.model.service;

import isu.library.model.entity.LibraryReservation;
import isu.library.model.entity.Reservation;
import isu.library.model.entity.UserReservation;
import isu.library.model.repository.PersonRepository;
import isu.library.model.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;

@Transactional
@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Iterable<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public void addNewReservation(Integer bookId, Integer personId, Date dateFrom, Date dateTo, boolean borrow) {
        Reservation r = new Reservation(bookId, personId, dateFrom, dateTo, borrow);
        reservationRepository.save(r);
    }

    @Override
    public Iterable<LibraryReservation> findAllReservationsInLibrary(Integer libraryId) {
        Iterable<LibraryReservation> lr = entityManager.createNativeQuery("SELECT r.id, r.book_id, r.date_from, r.date_to, b.name, b.isbn, p.name, p.surname, p.birth_date, r.is_borrowed FROM blocking r INNER JOIN book b ON b.id = r.book_id INNER JOIN person p ON p.id = r.person_id WHERE b.library_id =" + libraryId, LibraryReservation.class).getResultList();
        return lr;
    }

    @Override
    public void deleteReservation(Integer reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public void switchToBorrow(Integer reservationId) {
        Reservation r = reservationRepository.findById(reservationId).orElse(null);
        if (r != null) {
            r.setBorrowed(true);
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(14);
            r.setDateFrom(Date.valueOf(startDate));
            r.setDateTo(Date.valueOf(endDate));
            reservationRepository.save(r);
        }
    }

    @Override
    public Iterable<UserReservation> findAllUserReservations(Integer personId) {
        Iterable<UserReservation> userRes = entityManager.createNativeQuery("SELECT r.id, r.book_id, r.date_from, r.date_to, b.name, b.isbn, r.is_borrowed FROM blocking r INNER JOIN book b ON b.id = r.book_id WHERE r.person_id =" + personId, UserReservation.class).getResultList();
        return userRes;
    }
}
