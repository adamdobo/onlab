package hu.adamdobo.onlabproject.items;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/13/2018.
 */

public interface SaveItemCallbackListener {

    void saveItemWithPicture(Item item, byte[] uri);

}
