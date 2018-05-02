package hu.adamdobo.onlabproject.delivery;

import java.util.List;

import hu.adamdobo.onlabproject.model.DeliveryItem;

/**
 * Created by Ádám on 4/10/2018.
 */

public interface DeliveryPresenter {

    void onDestroy();

    List<DeliveryItem> getDeliveries();

    void onDeliveryItemsLoaded();

    void closeDelivery(DeliveryItem deliveryItem);
}
