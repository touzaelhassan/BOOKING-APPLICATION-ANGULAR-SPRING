package com.application.services.implementations;

import com.application.entities.Client;
import com.application.entities.Reservation;
import com.application.entities.Room;
import com.application.repositories.ReservationRepository;
import com.application.services.specifications.ReservationServiceSpecification;
import com.application.services.specifications.RoomServiceSpecification;
import com.application.services.specifications.UserServiceSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("reservationServiceBean")
public class ReservationServiceImplementation implements ReservationServiceSpecification {

    private final UserServiceSpecification userServiceBean;
    private final RoomServiceSpecification roomServiceBean;
    private final ReservationRepository reservationRepositoryBean;

    public ReservationServiceImplementation(ReservationRepository reservationRepositoryBean, UserServiceSpecification userServiceBean, RoomServiceSpecification roomServiceBean) {
        this.reservationRepositoryBean = reservationRepositoryBean;
        this.userServiceBean = userServiceBean;
        this.roomServiceBean = roomServiceBean;
    }

    @Override
    public Reservation addReservation(Reservation reservation) { return reservationRepositoryBean.save(reservation); }
    @Override
    public Reservation addReservation(Integer clientId, Integer roomId, String checking, String checkout) {

        Client client = (Client) userServiceBean.findUserById(clientId);
        Room room = roomServiceBean.getRoomById(roomId);
        Reservation reservation = new Reservation();
        reservation.setChecking(checking);
        reservation.setCheckout(checkout);
        reservation.setClient(client);
        reservation.setRoom(room);
        reservation.setApproved(false);
        return reservationRepositoryBean.save(reservation);
    }
    @Override
    public Reservation getReservationById(Integer id) { return reservationRepositoryBean.findById(id).orElse(null); }
    @Override
    public Reservation changeReservationStatus(Integer id) {
        Reservation reservation = this.getReservationById(id);
        reservation.setApproved(!reservation.isApproved());
        return reservationRepositoryBean.save(reservation);
    }
    @Override
    public List<Reservation> getReservations() { return reservationRepositoryBean.findAll(); }
    @Override
    public List<Reservation> getReservationsByClientId(Integer clientId) { return reservationRepositoryBean.findByClientId(clientId); }
    @Override
    public List<Reservation> findReservationByOwnerId(Integer ownerId) { return reservationRepositoryBean.findByOwnerId(ownerId); }
    @Override
    public void deleteReservation(Integer id) { reservationRepositoryBean.deleteById(id);}

}
