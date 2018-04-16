package hu.adamdobo.onlabproject.delivery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.adamdobo.onlabproject.R;

/**
 * Created by Ádám on 4/10/2018.
 */

public class DeliveryFragment extends Fragment implements DeliveryView{
    private RecyclerView recyclerView;
    private DeliveryAdapter deliveryAdapter;
    private DeliveryPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new DeliveryPresenterImpl(this, new DeliveryInteractorImpl());
        View contentView = inflater.inflate(R.layout.simple_recyclerview, container, false);
        setRecyclerView(contentView);
        return contentView;
    }

    public static Fragment newInstance() {
        return new DeliveryFragment();
    }

    public void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.itemsList);
        deliveryAdapter = new DeliveryAdapter(getActivity());
        recyclerView.setAdapter(deliveryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void setDeliveryItems() {
        deliveryAdapter.addItems(presenter.getDeliveries());
    }
}
