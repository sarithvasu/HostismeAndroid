package com.effone.hostismeandroid.model_for_json;

/**
 * Created by sarith.vasu on 22-06-2017.
 */

public class MenuTaxItens {
    private String chargevalue;

    private String name;

    private String type;

    public String getChargevalue ()
    {
        return chargevalue;
    }

    public void setChargevalue (String chargevalue)
    {
        this.chargevalue = chargevalue;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [chargevalue = "+chargevalue+", name = "+name+", type = "+type+"]";
    }
}
