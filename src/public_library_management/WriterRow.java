package public_library_management;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author KIshore
 */
public class WriterRow {

    // SimpleStringProperty containid;

    SimpleStringProperty bookid;
    SimpleStringProperty authorid;

    WriterRow(String[] s) {
        // int i = 0 ;
        // System.out.println("length" + s.length + s[0] + " " + s[1] + " " + s[9]);
        // this.containid = new SimpleStringProperty(s[0]);

        this.bookid = new SimpleStringProperty(s[0]);
        this.authorid = new SimpleStringProperty(s[1]);

    }

    public void setBookid(String bookid) {
        if (bookid.matches("[0-9]+") == true) {
            SimpleStringProperty temp = new SimpleStringProperty(bookid);
            this.bookid = temp;
        }

    }

    public void setAuthorid(String authorid) {
        if (authorid.matches("[0-9]+") == true) {
            SimpleStringProperty temp = new SimpleStringProperty(authorid);
            this.authorid = temp;
        }

    }

    // public String getContainid() {
    // return containid.getValue();
    // }

    public String getBookid() {
        return bookid.getValue();
    }

    public String getAuthorid() {
        return authorid.getValue();
    }

}
