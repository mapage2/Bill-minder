package com.example.amasio.bill_minder;

/**
 * Created by amasio on 12/10/16.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {

    private String title;
    private double amount;
    private Date expenseDate;

    private DateFormat formatter = new SimpleDateFormat("MM/dd/yyy");

    public Expense(){

        setTitle("");
        setAmount(0.0);
        setExpenseDate("");
    }

    public Expense(String title, Double amount, String expenseDate){

        this.setTitle(title);
        this.setAmount(amount);
        this.setExpenseDate(expenseDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        try {
            this.expenseDate = formatter.parse(expenseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override public String toString(){

        String expense = getTitle() +" "+getAmount()+" "+formatter.format(getExpenseDate());
        return expense;

    }
}
