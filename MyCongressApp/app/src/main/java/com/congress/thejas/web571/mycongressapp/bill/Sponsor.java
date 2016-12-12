package com.congress.thejas.web571.mycongressapp.bill;

/**
 * Created by Thejas on 11/20/2016.
 */

public class Sponsor {

   String first_name;
       String     nickname;
         String   last_name;
         String   middle_name;
       String     name_suffix;
          String  title;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getName_suffix() {
        return name_suffix;
    }

    public void setName_suffix(String name_suffix) {
        this.name_suffix = name_suffix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
