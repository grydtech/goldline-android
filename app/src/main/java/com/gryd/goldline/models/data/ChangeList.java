package com.gryd.goldline.models.data;

import java.util.ArrayList;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class ChangeList {
    // This class stores all the changes made to the inventory until they are synced online
    private ArrayList<Change> changes;

    private void addChange(Change action) {
        changes.add(action);
    }

    private void commitChanges() {
        for (Change action : changes) {
            // Sync with online database
            continue;
        }
        changes.clear();
    }
}
