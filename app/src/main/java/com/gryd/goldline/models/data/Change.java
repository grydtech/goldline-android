package com.gryd.goldline.models.data;

import com.gryd.goldline.models.Item;

/**
 * Created By: Yasith Jayawardana
 * Email: yasith.jayawardana@icloud.com
 * Date: 11/4/17
 */

public class Change {

    private ChangeAction changeAction;
    private Item changedItem;
    private int stockDelta;

    public static Change newItemInsert(Item item) {
        Change change = new Change();
        change.changeAction = ChangeAction.Insert;
        change.changedItem = item;
        return change;
    }

    public static Change newItemUpdate(Item updatedItem) {
        Change change = new Change();
        change.changeAction = ChangeAction.ItemUpdate;
        change.changedItem = updatedItem;
        return change;
    }

    public static Change newStockUpdate(Item updatedItem, int amount) {
        Change change = new Change();
        change.changeAction = ChangeAction.StocksUpdate;
        change.changedItem = updatedItem;
        change.stockDelta = amount;
        return change;
    }

    public static Change newItemDelete(Item item) {
        Change change = new Change();
        change.changeAction = ChangeAction.Delete;
        change.changedItem = item;
        return change;
    }

    public ChangeAction getChangeAction() {
        return changeAction;
    }

    public Item getChangedItem() {
        return changedItem;
    }


    public int getStockDelta() {
        return stockDelta;
    }

}
