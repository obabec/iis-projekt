package isu.library.model.service;

import isu.library.model.entity.Reservation;
import isu.library.model.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Transactional
@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Iterable<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public void addNewReservation(Integer bookId, Integer personId, Date dateFrom, Date dateTo, boolean borrow) {
        Reservation r = new Reservation(bookId, personId, dateFrom, dateTo, borrow);
        reservationRepository.save(r);
    }
}
