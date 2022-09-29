package com.javatpoint.repository;

import com.javatpoint.model.BankStatement;
import org.springframework.data.repository.CrudRepository;

public interface BankStatementRepository extends CrudRepository<BankStatement, Integer>
{
}
