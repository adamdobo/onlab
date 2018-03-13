package hu.adamdobo.onlabproject.items;

import java.util.List;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/12/2018.
 */

public interface ItemsInteractor {

    List<Item> getAllItems();

    void subscribeToDatabaseChanges();

    void saveItem(Item item);

    void setPresenter(ItemsPresenter presenter);
}
