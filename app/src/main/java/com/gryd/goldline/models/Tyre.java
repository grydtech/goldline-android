package com.gryd.goldline.models;

import java.time.Year;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class Tyre extends Item {
    private String Size;
    private String Country;
    private Year Make;

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public Year getMake() {
        return Make;
    }

    public void setMake(Year make) {
        Make = make;
    }
}
