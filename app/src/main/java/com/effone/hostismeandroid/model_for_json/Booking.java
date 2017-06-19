package com.effone.hostismeandroid.model_for_json;

/**
 * Created by sarith.vasu on 16-06-2017.
 */

public class Booking {
    private String status;

    private Bookinghistory[] bookinghistory;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Bookinghistory[] getBookinghistory ()
    {
        return bookinghistory;
    }

    public void setBookinghistory (Bookinghistory[] bookinghistory)
    {
        this.bookinghistory = bookinghistory;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", bookinghistory = "+bookinghistory+"]";
    }

}
