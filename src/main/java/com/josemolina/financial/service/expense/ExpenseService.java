package com.josemolina.financial.service.expense;

import com.josemolina.financial.model.Expense;
import com.josemolina.financial.model.User;

import java.util.List;

public interface ExpenseService {

    List<Expense> findAll();

    Expense findById(String id);

    Expense save(Expense expense);

    Expense update(String id, Expense expense);

    void deleteById(String id);

    List<Expense> findByUser(User user);

    List<Expense> findIncomesForAuthenticatedUser();

}