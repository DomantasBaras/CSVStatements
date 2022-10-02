package com.javatpoint.model;
import javax.persistence.*;
import java.util.Date;

//mark class as an Entity
@Entity
//defining class name as Table name
@Table
public class BankStatement
{

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String AccountId;
    @Column
    private String Benef;
    @Column
    private String Comment;
    @Column
    private double Amount;
    @Column
    private Date OpDate;
    @Column
    private String Currency;


    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getAccountId()
    {
        return AccountId;
    }
    public void setAccountId(String accountId)
    {
        this.AccountId = accountId;
    }

    public String getBenef()
    {
        return Benef;
    }
    public void setBenef(String benef)
    {
        this.Benef = benef;
    }

    public String getComment()
    {
        return Benef;
    }
    public void setComment(String comment)
    {
        this.Comment = comment;
    }

    public Double getAmount()
    {
        return Amount;
    }
    public void setComment(Double amount)
    {
        this.Amount = amount;
    }

    public Date getOpDate()
    {
        return OpDate;
    }
    public void setOpDate(Date opDate)
    {
        this.OpDate = opDate;
    }
    public void setAmount(double amount){
        this.Amount = amount;
    }

    public String getCurrency()
    {
        return Currency;
    }
    public void setCurrency(String currency)
    {
        this.Currency = currency;
    }
}