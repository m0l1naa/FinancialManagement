package com.josemolina.financial.repository;

import com.josemolina.financial.model.FinancialSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialSummaryRepository extends MongoRepository<FinancialSummary, String> {
}