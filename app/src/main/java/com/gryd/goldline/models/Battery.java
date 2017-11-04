package com.gryd.goldline.models;

import java.time.Period;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class Battery extends Item {
    private int Capacity;
    private Period Warranty;

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public Period getWarranty() {
        return Warranty;
    }

    public void setWarranty(Period warranty) {
        Warranty = warranty;
    }
}
