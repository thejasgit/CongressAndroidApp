package com.congress.thejas.web571.mycongressapp.bill;

/**
 * Created by Thejas on 11/20/2016.
 */

public class History {


   Boolean active;
    String active_at;
    String awaiting_signature;
    String enacted;
    String senate_passage_result;
    String senate_passage_result_at;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getActive_at() {
        return active_at;
    }

    public void setActive_at(String active_at) {
        this.active_at = active_at;
    }

    public String getAwaiting_signature() {
        return awaiting_signature;
    }

    public void setAwaiting_signature(String awaiting_signature) {
        this.awaiting_signature = awaiting_signature;
    }

    public String getEnacted() {
        return enacted;
    }

    public void setEnacted(String enacted) {
        this.enacted = enacted;
    }

    public String getSenate_passage_result() {
        return senate_passage_result;
    }

    public void setSenate_passage_result(String senate_passage_result) {
        this.senate_passage_result = senate_passage_result;
    }

    public String getSenate_passage_result_at() {
        return senate_passage_result_at;
    }

    public void setSenate_passage_result_at(String senate_passage_result_at) {
        this.senate_passage_result_at = senate_passage_result_at;
    }

    public String getVetoed() {
        return vetoed;
    }

    public void setVetoed(String vetoed) {
        this.vetoed = vetoed;
    }

    String vetoed;
}
