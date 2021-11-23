/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.components;

import isu.library.model.entity.reservation.Reservation;
import isu.library.model.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class ReservationCleaner {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationCleaner.class);

    private final ReservationRepository reservationRepository;

    public ReservationCleaner(final ReservationRepository eventRepository) {
        this.reservationRepository = eventRepository;
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void cleanOldReservations() {
        LOG.info("CLEANING");
        LocalDate today = LocalDate.now();
        Date sqlToday = Date.valueOf(today);
        Iterable<Reservation> reservations = reservationRepository.findReservationByDateFromBefore(sqlToday);
        for (Reservation res : reservations) {
            if (res.getBorrowed() == null) continue;
            if (res.getBorrowed()) {
                res.setBorrowed(null);
                reservationRepository.save(res);
            } else if (!res.getBorrowed()) {
                reservationRepository.deleteById(res.getId());
            }
        }
    }
}
