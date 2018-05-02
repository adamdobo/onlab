package hu.adamdobo.onlabproject.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.dialog.CloseDeliveryDialog;
import hu.adamdobo.onlabproject.locationservice.MyLocationService;
import hu.adamdobo.onlabproject.model.DeliveryItem;

/**
 * Created by Ádám on 4/10/2018.
 */

public class DeliveryFragment extends Fragment implements DeliveryView, DeliveryDoneListener, DeliveryAdapter.OnDeliveryCloseListener{
    private RecyclerView recyclerView;
    private DeliveryAdapter deliveryAdapter;
    private DeliveryPresenter presenter;

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new DeliveryPresenterImpl(this, new DeliveryInteractorImpl());
        getActivity().setTitle(R.string.deliveries);
        View contentView = inflater.inflate(R.layout.simple_recyclerview, container, false);
        setRecyclerView(contentView);
        return contentView;
    }

    public static Fragment newInstance() {
        return new DeliveryFragment();
    }

    public void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.itemsList);
        deliveryAdapter = new DeliveryAdapter(getActivity(), this);
        recyclerView.setAdapter(deliveryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void setDeliveryItems() {
        deliveryAdapter.addItems(presenter.getDeliveries());
    }

    @Override
    public void closeDelivery(DeliveryItem deliveryItem) {
        presenter.closeDelivery(deliveryItem);
        deliveryAdapter.deleteItem(deliveryItem);
    }

    @Override
    public void onDeliveryCloseClicked(View view, int pos) {
        CloseDeliveryDialog closeDeliveryDialog = CloseDeliveryDialog.newInstance(this, deliveryAdapter.getItem(pos));
        closeDeliveryDialog.show(getActivity().getSupportFragmentManager(), CloseDeliveryDialog.TAG);

    }

    @Override
    public void stopDeliveryService() {
        Intent deliveryIntent = new Intent(getActivity(), MyLocationService.class);
        getActivity().stopService(deliveryIntent);
        Snackbar.make(getView(), "You successfully delivered the item!", Snackbar.LENGTH_LONG).show();
    }
}
