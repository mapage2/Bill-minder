package com.example.amasio.bill_minder;

/**
 * Created by amasio on 12/10/16.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bill {
    private String title;
    private String companyName;
    private Date startDate;                 //Add in duration
    private Date endDate;
    private Date billDate;                 //may change this into an integer
    private Double amount;
    private Occurrence billType;

    private DateFormat formatter = new SimpleDateFormat("MM/dd/yyy");

    public Bill(){
        setTitle("");
        setCompanyName("");
        setStartDate("");
        setEndDate("");
        setBillDate("");
        setAmount(0.0);
        setBillType("monthly");
    }

    public Bill(String title, String companyName, String startDate, String endDate, String billDate,
                Double amount, String billType){
        this.setTitle(title);
        this.setCompanyName(companyName);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setBillDate(billDate);
        this.setAmount(amount);
        this.setBillType(billType);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        try {
            this.startDate = formatter.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        try {
            this.endDate = formatter.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        try {
            this.billDate = formatter.parse(billDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Occurrence getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        if (billType.equalsIgnoreCase("daily")) {
            this.billType = Occurrence.DAILY;
        } else if (billType.equalsIgnoreCase("weekly")){
            this.billType = Occurrence.WEEKLY;
        } else if (billType.equalsIgnoreCase("yearly")){
            this.billType = Occurrence.YEARLY;
        }else if (billType.equalsIgnoreCase("quarterly")){
            this.billType = Occurrence.QUARTERLY;
        }else {
            this.billType = Occurrence.MONTHLY;
        }
    }

    @Override public String toString(){

        String bill = getTitle() +" "+getCompanyName()+" "+formatter.format(getStartDate())+" "
                +formatter.format(getEndDate())+" "+formatter.format(getBillDate())+" "+getAmount();
        if(billType == Occurrence.MONTHLY){
            bill+=" Monthly";
        }
        return bill;

    }
}

