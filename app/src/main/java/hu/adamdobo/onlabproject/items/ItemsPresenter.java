package hu.adamdobo.onlabproject.items;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/12/2018.
 */

public interface ItemsPresenter {

    void onDestroy();

    void saveItemWithPicture(Item item, byte[] bytes);

    Item getAddedItem();

    Item getDeletedItem();

    Item getChangedItem();

    void onItemChanged();

    void onItemAdded();

    void onItemDeleted();

    void onItemSaveSuccess();
}