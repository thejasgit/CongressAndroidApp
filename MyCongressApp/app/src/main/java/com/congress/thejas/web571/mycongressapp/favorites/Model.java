package com.congress.thejas.web571.mycongressapp.favorites;

import com.congress.thejas.web571.mycongressapp.bill.BillItem;
import com.congress.thejas.web571.mycongressapp.commitee.CommitteeItem;
import com.congress.thejas.web571.mycongressapp.legislator.LegislatorItem;

import java.util.List;

/**
 * Created by Thejas on 11/21/2016.
 */

public class Model {


    static Model instance = null;
    private String legislator,committee,bills;

    public String getLegislator() {
        return legislator;
    }

    public void setLegislator(String legislator) {
        this.legislator = legislator;
    }

    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }

    public String getBills() {
        return bills;
    }

    public void setBills(String bills) {
        this.bills = bills;
    }

    private Model(){



    }

    static public Model getInstance(){
        if(instance==null){
            instance = new Model();
        }
        return instance;

    }


}
