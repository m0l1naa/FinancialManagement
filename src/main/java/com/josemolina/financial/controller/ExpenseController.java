package com.josemolina.financial.controller;

import com.josemolina.financial.model.Category;
import com.josemolina.financial.model.Expense;
import com.josemolina.financial.service.category.CategoryService;
import com.josemolina.financial.service.expense.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Expense> findAll() {
        return expenseService.findAll();
    }

    @GetMapping("/{idExpense}")
    public Expense findById(@PathVariable String idExpense) {
        return expenseService.findById(idExpense);
    }

    @PostMapping("/{idCategory}")
    public Expense save(@PathVariable String idCategory, @RequestBody Expense expense) {

        if (expense.getCategory() == null) {
            Category categoryFound = categoryService.findById(idCategory);
            System.out.println(categoryFound);
            expense.setCategory(categoryFound);
        }

        return expenseService.save(expense);
    }

    @PutMapping("/{idExpense}")
    public Expense update(@PathVariable String idExpense, @RequestBody Expense expense) {
        return expenseService.update(idExpense, expense);
    }

    @DeleteMapping("/{idExpense}")
    public void deleteById(@PathVariable String idExpense) {
        expenseService.deleteById(idExpense);
    }

    @GetMapping("/my-expenses")
    public List<Expense> findIncomesForAuthenticatedUser() {
        return expenseService.findIncomesForAuthenticatedUser();
    }

}