package com.congress.thejas.web571.mycongressapp.bill;

/**
 * Created by Thejas on 11/20/2016.
 */

public class Latestversion {

    String version_code,issued_on,version_name,bill_version_id;
    Url urls;

    public Url getUrls() {
        return urls;
    }

    public void setUrls(Url urls) {
        this.urls = urls;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getIssued_on() {
        return issued_on;
    }

    public void setIssued_on(String issued_on) {
        this.issued_on = issued_on;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getBill_version_id() {
        return bill_version_id;
    }

    public void setBill_version_id(String bill_version_id) {
        this.bill_version_id = bill_version_id;
    }
}
