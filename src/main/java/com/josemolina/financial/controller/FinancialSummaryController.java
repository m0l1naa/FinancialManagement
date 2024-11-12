package com.josemolina.financial.controller;

import com.josemolina.financial.model.FinancialSummary;
import com.josemolina.financial.model.User;
import com.josemolina.financial.service.financial.FinancialSummaryService;
import com.josemolina.financial.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/financials")
public class FinancialSummaryController {

    @Autowired
    private FinancialSummaryService financialSummaryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<FinancialSummary> getFinancialSummary(@PathVariable String userId, @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from, @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        User userFound = userService.findById(userId);

        FinancialSummary summary = financialSummaryService.generateSummary(userFound, from, to);
        return ResponseEntity.ok(summary);
    }

}