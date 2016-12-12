package com.congress.thejas.web571.mycongressapp.bill;

import com.congress.thejas.web571.mycongressapp.bill.History;
import com.congress.thejas.web571.mycongressapp.bill.Latestversion;
import com.congress.thejas.web571.mycongressapp.bill.Sponsor;
import com.congress.thejas.web571.mycongressapp.bill.Url;

/**
 * Created by Thejas on 11/20/2016.
 */

public class BillItem {

    String bill_id;
    String short_title,official_title;
    String introduced_on;
    String chamber,bill_type;
    History history;
    Sponsor sponsor;

    public Latestversion getLast_version() {
        return last_version;
    }

    public void setLast_version(Latestversion last_version) {
        this.last_version = last_version;
    }

    Latestversion last_version;
    Url urls;

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }



    public Url getUrls() {
        return urls;
    }

    public void setUrls(Url urls) {
        this.urls = urls;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public String getOfficial_title() {
        return official_title;
    }

    public void setOfficial_title(String official_title) {
        this.official_title = official_title;
    }

    public String getIntroduced_on() {
        return introduced_on;
    }

    public void setIntroduced_on(String introduced_on) {
        this.introduced_on = introduced_on;
    }
}
