package org.example.finalfabricio300380186.repositories;

import org.example.finalfabricio300380186.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatsRepository extends JpaRepository<Seat, Long> {


    boolean existsBySeatNum(String seatNum);
    long countBySeatNumIsNull();

}