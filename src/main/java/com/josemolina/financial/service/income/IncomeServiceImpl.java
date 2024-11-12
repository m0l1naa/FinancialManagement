package com.josemolina.financial.service.income;

import com.josemolina.financial.model.Income;
import com.josemolina.financial.model.User;
import com.josemolina.financial.repository.IncomeRepository;
import com.josemolina.financial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Income> findAll() {
        return incomeRepository.findAll();
    }

    @Override
    public Income findById(String id) {
        return incomeRepository.findById(id).orElse(null);
    }

    @Override
    public Income save(Income income) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedId = (String) authentication.getPrincipal();

        System.out.println(authenticatedId);
        User authenticatedUser = userRepository.findById(authenticatedId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        income.setUser(authenticatedUser);

        if (income.getDate() == null) {
            income.setDate(LocalDate.now());
        }

        return incomeRepository.save(income);
    }

    @Override
    public Income update(String id, Income income) {
        Income existingIncome = incomeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ingreso no encontrado"));

        if (income.getAmount() != null) {
            existingIncome.setAmount(income.getAmount());
        }

        if (income.getDate() != null) {
            existingIncome.setDate(income.getDate());
        }

        if (income.getSource() != null) {
            existingIncome.setSource(income.getSource());
        }

        if (income.getAmount() != null) {
            existingIncome.setAmount(income.getAmount());
        }

        if (income.getUser() != null) {
            existingIncome.setUser(income.getUser());
        }

        return incomeRepository.save(existingIncome);
    }

    @Override
    public void deleteById(String id) {
        incomeRepository.deleteById(id);
    }

    @Override
    public List<Income> findByUser(User user) {
        return incomeRepository.findByUser(user);
    }

    @Override
    public List<Income> findIncomesForAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedId = (String) authentication.getPrincipal();

        User authenticatedUser = userRepository.findById(authenticatedId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return incomeRepository.findByUser(authenticatedUser);
    }

}