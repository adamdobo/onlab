package hu.adamdobo.onlabproject.delivery;

import java.util.List;

import hu.adamdobo.onlabproject.model.DeliveryItem;

/**
 * Created by Ádám on 4/10/2018.
 */

public class DeliveryPresenterImpl implements DeliveryPresenter {
    private DeliveryView deliveryView;
    private DeliveryInteractor deliveryInteractor;

    public DeliveryPresenterImpl(DeliveryView deliveryView, DeliveryInteractor deliveryInteractor) {
        this.deliveryView = deliveryView;
        this.deliveryInteractor = deliveryInteractor;
        deliveryInteractor.setPresenter(this);
        deliveryInteractor.subscribeToDatabaseChanges();
    }

    @Override
    public void onDestroy() {
        deliveryView = null;
    }

    @Override
    public List<DeliveryItem> getDeliveries() {
        return deliveryInteractor.getDeliveries();
    }

    @Override
    public void onDeliveryItemsLoaded() {
        if(deliveryView!=null) {
            deliveryView.setDeliveryItems();
        }
    }

    @Override
    public void closeDelivery(DeliveryItem deliveryItem) {
        deliveryInteractor.closeDelivery(deliveryItem);
    }
}
