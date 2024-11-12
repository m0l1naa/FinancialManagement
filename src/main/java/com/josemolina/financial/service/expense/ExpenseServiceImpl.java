package com.josemolina.financial.service.expense;

import com.josemolina.financial.model.Expense;
import com.josemolina.financial.model.User;
import com.josemolina.financial.repository.ExpenseRepository;
import com.josemolina.financial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense findById(String id) {
        return expenseRepository.findById(id).orElse(null);
    }

    @Override
    public Expense save(Expense expense) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedId = (String) authentication.getPrincipal();

        System.out.println("Id del autenticado: " + authenticatedId);
        User authenticatedUser = userRepository.findById(authenticatedId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        expense.setUser(authenticatedUser);

        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }

        return expenseRepository.save(expense);
    }

    @Override
    public Expense update(String id, Expense expense) {

        Expense existingExpense = expenseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Gasto no encontrado"));

        if (expense.getDescription() != null) {
            existingExpense.setDescription(expense.getDescription());
        }

        if (expense.getAmount() != null) {
            existingExpense.setAmount(expense.getAmount());
        }

        if (expense.getDate() != null) {
            existingExpense.setDate(expense.getDate());
        }

        if (expense.getUser() != null) {
            existingExpense.setUser(expense.getUser());
        }

        if (expense.getCategory() != null) {
            existingExpense.setCategory(expense.getCategory());
        }

        return expenseRepository.save(existingExpense);

    }

    @Override
    public void deleteById(String id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public List<Expense> findByUser(User user) {
        return expenseRepository.findByUser(user);
    }

    @Override
    public List<Expense> findIncomesForAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedId = (String) authentication.getPrincipal();

        User authenticatedUser = userRepository.findById(authenticatedId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return expenseRepository.findByUser(authenticatedUser);
    }

}