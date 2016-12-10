package com.example.amasio.bill_minder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amasio on 12/10/16.
 */

public class Subscription {

    private String title;
    private String companyName;
    private Date startDate;                 //Add in duration
    private Date endDate;
    private Date paymentDate;                 //may change this into an integer
    private Double amount;
    private Occurrence subscriptionType;

    private DateFormat formatter = new SimpleDateFormat("MM/dd/yyy");

    public Subscription(){
        setTitle("");
        setCompanyName("");
        setStartDate("");
        setEndDate("");
        setPaymentDate("");
        setAmount(0.0);
        setSubscriptionType("monthly");
    }

    public Subscription(String title, String companyName, String startDate, String endDate, String paymentDate,
                Double amount, String subscriptionType){
        this.setTitle(title);
        this.setCompanyName(companyName);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setPaymentDate(paymentDate);
        this.setAmount(amount);
        this.setSubscriptionType(subscriptionType);
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        try {
            this.paymentDate = formatter.parse(paymentDate);
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

    public Occurrence getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        if (subscriptionType.equalsIgnoreCase("daily")) {
            this.subscriptionType = Occurrence.DAILY;
        } else if (subscriptionType.equalsIgnoreCase("weekly")){
            this.subscriptionType = Occurrence.WEEKLY;
        } else if (subscriptionType.equalsIgnoreCase("yearly")){
            this.subscriptionType = Occurrence.YEARLY;
        }else if (subscriptionType.equalsIgnoreCase("quarterly")){
            this.subscriptionType = Occurrence.QUARTERLY;
        }else {
            this.subscriptionType = Occurrence.MONTHLY;
        }
    }

    @Override public String toString(){

        String sub = getTitle() +" "+getCompanyName()+" "+formatter.format(getStartDate())+" "
                +formatter.format(getEndDate())+" "+formatter.format(getPaymentDate())+" "+getAmount();
        if(subscriptionType == Occurrence.MONTHLY){
            sub+=" Monthly";
        }
        return sub;

    }
}
