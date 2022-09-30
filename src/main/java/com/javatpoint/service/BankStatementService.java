package com.javatpoint.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javatpoint.model.BankStatement;
import com.javatpoint.repository.BankStatementRepository;
//defining the business logic
@Service
public class BankStatementService
{
@Autowired
BankStatementRepository bankStatementRepository;
//getting all student records

public List<BankStatement> getAllAccounts()
{
    List<BankStatement> statements = new ArrayList<BankStatement>();
    bankStatementRepository.findAll().forEach(statement -> statements.add(statement));
    return statements;
}

    public void saveOrUpdate(BankStatement account)
    {
        bankStatementRepository.save(account);
    }
}