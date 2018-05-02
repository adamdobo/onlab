package hu.adamdobo.onlabproject.delivery;

import hu.adamdobo.onlabproject.model.DeliveryItem;

/**
 * Created by Ádám on 5/1/2018.
 */

public interface DeliveryDoneListener {
    void closeDelivery(DeliveryItem deliveryItem);
}
