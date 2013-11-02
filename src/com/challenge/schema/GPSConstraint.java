package com.challenge.schema;

/**
* Created with IntelliJ IDEA.
* User: Binnie
* Date: 27/10/13
* Time: 13:13
* To change this template use File | Settings | File Templates.
*/
public class GPSConstraint {
    public double lat;
    public double lon;
    public double range;

    @Override
    public String toString(){
        return "" + lat + "," + lon + "," + range;
    }

}
