package com.javatpoint.repository;
import org.springframework.data.repository.CrudRepository;
import com.javatpoint.model.BankStatement;
public interface BankStatementRepository extends CrudRepository<BankStatement, Integer>
{
}
