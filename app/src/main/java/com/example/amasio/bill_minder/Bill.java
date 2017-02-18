package com.example.amasio.bill_minder;

/**
 * Created by amasio on 12/10/16.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bill {

    private String ownerId;
    private String title;
    private String companyName;
    private String startDate;                 //For Firebase database convenience
    //private Date startDate;                 //Actual startDate type
    private String endDate;                   //For Firebase database convenience
    //private Date endDate;                   //Actual endDate type
    private int billDate;
    private Double amount;
    //private Occurrence billType;            //ENUM for Bill Occurrence
    private String billType;                  //For Firebase database convenience

    private DateFormat formatter = new SimpleDateFormat("MM/dd/yyy");

    public Bill(){
        setOwnerId("");
        setTitle("");
        setCompanyName("");
        setStartDate("");
        setEndDate("");
        setBillDate(0);
        setAmount(0.0);
        setBillType("monthly");
    }

    public Bill(String ownerId, String title, String companyName, String startDate, String endDate, int billDate,
                Double amount, String billType){
        this.setOwnerId(ownerId);
        this.setTitle(title);
        this.setCompanyName(companyName);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setBillDate(billDate);
        this.setAmount(amount);
        this.setBillType(billType);
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getStartDate() {
        return startDate;
    }

//    public Date getStartDate() {
//        return startDate;
//    }

    public void setStartDate(String startDate) {
//        try {
//            this.startDate = formatter.parse(startDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

//    public Date getEndDate() {
//        return endDate;
//    }

    public void setEndDate(String endDate) {
//        try {
//            this.endDate = formatter.parse(endDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        this.endDate = endDate;
    }

    public int getBillDate() {
        return billDate;
    }

//    public Date getBillDate() {
//        return billDate;
//    }

    public void setBillDate(int billDate) {
//        try {
//            this.billDate = formatter.parse(billDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        this.billDate = billDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

//    public Occurrence getBillType() {
//        return billType;
//    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {

//        if (billType.equalsIgnoreCase("daily")) {
//            this.billType = Occurrence.DAILY;
//        } else if (billType.equalsIgnoreCase("weekly")){
//            this.billType = Occurrence.WEEKLY;
//        } else if (billType.equalsIgnoreCase("yearly")){
//            this.billType = Occurrence.YEARLY;
//        }else if (billType.equalsIgnoreCase("quarterly")){
//            this.billType = Occurrence.QUARTERLY;
//        }else {
//            this.billType = Occurrence.MONTHLY;
//        }

        if (billType.equalsIgnoreCase("daily")) {
            this.billType = "Daily";
        } else if (billType.equalsIgnoreCase("weekly")){
            this.billType = "Weekly";
        } else if (billType.equalsIgnoreCase("yearly")){
            this.billType = "Yearly";
        }else if (billType.equalsIgnoreCase("quarterly")){
            this.billType = "Quarterly";
        }else {
            this.billType = "Monthly";
        }
    }

    @Override public String toString(){

//        String bill = getTitle() +" "+getCompanyName()+" "+formatter.format(getStartDate())+" "
//                +formatter.format(getEndDate())+" "+formatter.format(getBillDate())+" "+getAmount();
//        if(billType.equalsIgnoreCase("monthly")){
//            bill+=" Monthly";
//        }
//        return bill;

        String bill = getOwnerId() +" "+ getTitle() +" "+getCompanyName()+" "+getStartDate()+" "
                +getEndDate()+" "+getBillDate()+" "+getAmount();
        if(billType.equalsIgnoreCase("monthly")){
            bill+=" Monthly";
        }
        return bill;


    }
}

