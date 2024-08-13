package org.example.finalfabricio300380186.web;

import lombok.AllArgsConstructor;
import org.example.finalfabricio300380186.entities.Seat;
import org.example.finalfabricio300380186.repositories.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class SeatController {

    @Autowired
    private SeatsRepository seatsRepository;

    private static final int TOTAL_SEATS = 20;

    @GetMapping("/index")
    public String index(Model model) {
        List<Seat> seatList = seatsRepository.findAll();
        long reservedSeats = seatsRepository.countBySeatNumIsNull();
        long remainingSeats = TOTAL_SEATS - reservedSeats;

        Map<String, String> seatMap = new HashMap<>();
        for (Seat seat : seatList) {
            seatMap.put(seat.getSeatNum(), seat.getName());
        }

        model.addAttribute("listSeats", seatList);
        model.addAttribute("remainingSeats", remainingSeats);
        model.addAttribute("seatMap", seatMap);
        return "index";
    }

    @PostMapping("/reserve")
    public String reserveSeat(@RequestParam("seatCode") String seatCode,
                              @RequestParam("customerName") String customerName,
                              @RequestParam("transactionDate") String transactionDate,
                              Model model) {
        if (seatCode == null || seatCode.isEmpty() || !isValidSeat(seatCode)) {
            model.addAttribute("error", "Invalid seat number.");
            return index(model);
        }

        if (seatsRepository.existsBySeatNum(seatCode)) {
            model.addAttribute("error", "Seat is already reserved.");
            return index(model);
        }

        Seat newSeat = new Seat();
        newSeat.setSeatNum(seatCode);
        newSeat.setName(customerName);

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(transactionDate);
            newSeat.setDot(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        seatsRepository.save(newSeat);

        // Recalculate remaining seats
        return index(model);
    }

    private boolean isValidSeat(String seatCode) {
        return seatCode.matches("^[1-4][A-E]$");
    }

    @PostMapping("/delete")
    public String deleteSeat(@RequestParam("id") Long id, Model model) {
        seatsRepository.deleteById(id);
        model.addAttribute("message", "Seat deleted successfully.");
        return index(model);
    }

    @GetMapping("/edit")
    public String editSeatForm(@RequestParam("id") Long id, Model model) {
        Seat seat = seatsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid seat Id:" + id));
        model.addAttribute("seat", seat);
        return "edit";
    }

    @PostMapping("/edit")
    public String editSeat(@RequestParam("id") Long id,
                           @RequestParam("transactionCode") Long transactionCode,
                           @RequestParam("seatNum") String seatNum,
                           @RequestParam("customerName") String customerName,
                           @RequestParam("transactionDate") String transactionDate) {
        Seat seat = seatsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid seat Id:" + id));
        seat.setSeatNum(seatNum);
        seat.setName(customerName);
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(transactionDate);
            seat.setDot(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        seatsRepository.save(seat);
        return "redirect:/index";
    }
}