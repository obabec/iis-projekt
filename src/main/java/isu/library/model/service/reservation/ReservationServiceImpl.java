/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.service.reservation;

import isu.library.model.entity.library.LibraryReservation;
import isu.library.model.entity.reservation.Reservation;
import isu.library.model.entity.reservation.UserReservation;
import isu.library.model.repository.LibraryReservationRepository;
import isu.library.model.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Transactional
@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private LibraryReservationRepository libraryReservationRepository;

    @Override
    public Iterable<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Iterable<LibraryReservation> findAllReservationsInLibrary(Integer libraryId) {
        Iterable<LibraryReservation> lr = libraryReservationRepository.findAllByBookLibraryId(libraryId);
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
        for (UserReservation res : userRes) {
            Iterable<Reservation> nextRes = reservationRepository.findReservationsByBookIdAndDateFromGreaterThan(res.getBookId(), res.getDateTo());
            int i = 0;
            for (Reservation r : nextRes) {
                i++;
                break;
            }
            if (i != 0) {
                res.setExtendPossible(false);
            }
        }
        return userRes;
    }

    @Override
    public Optional<Reservation> findReservationByBookIdAndPersonId(Integer bookId, Integer personId) {
        return reservationRepository.findReservationByBookIdAndPersonId(bookId, personId);
    }

    @Override
    public void saveNewReservation(Integer bookId, Integer personId, LocalDate dateFrom) {
        Reservation res;
        if (dateFrom == null) {
            res = new Reservation(bookId, personId, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(2)), false);
        } else {
            res = new Reservation(bookId, personId, Date.valueOf(dateFrom), Date.valueOf(dateFrom.plusDays(2)), false);
        }
        reservationRepository.save(res);
    }

    @Override
    public int saveNewLoan(Integer bookId, Integer personId, LocalDate dateFrom) {
        Reservation res = new Reservation(bookId, personId, Date.valueOf(dateFrom), Date.valueOf(dateFrom.plusDays(14)), true);
        try {
            reservationRepository.save(res);
            return res.getId();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public Optional<Reservation> findReservationByLatestDate(Integer bookId) {
        return reservationRepository.findReservationByLatestDate(bookId);
    }

    @Override
    public Iterable<Reservation> findReservationsByBookIdAndDateFromGreaterThan(Integer bookId, Date startDate) {
        return reservationRepository.findReservationsByBookIdAndDateFromGreaterThan(bookId, startDate);
    }

    @Override
    public Optional<Reservation> findReservationById(Integer id) {
        return reservationRepository.findReservationById(id);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public void deleteByBookId(int bookId) {
        reservationRepository.deleteByBookId(bookId);
    }
}
