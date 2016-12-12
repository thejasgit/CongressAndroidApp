package com.congress.thejas.web571.mycongressapp.bill;

/**
 * Created by Thejas on 11/20/2016.
 */

public class Url {
    public String getCongress() {
        return congress;
    }

    public void setCongress(String congress) {
        this.congress = congress;
    }

    public String getGovtrack() {
        return govtrack;
    }

    public void setGovtrack(String govtrack) {
        this.govtrack = govtrack;
    }

    public String getOpencongress() {
        return opencongress;
    }

    public void setOpencongress(String opencongress) {
        this.opencongress = opencongress;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    String congress,govtrack,opencongress,html,pdf,xml;

}
