package com.effone.hostismeandroid.model;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */


public class Sample
{
    private Menu Menu;

    public Menu getMenu ()
    {
        return Menu;
    }

    public void setMenu (Menu Menu)
    {
        this.Menu = Menu;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Menu = "+Menu+"]";
    }
}
