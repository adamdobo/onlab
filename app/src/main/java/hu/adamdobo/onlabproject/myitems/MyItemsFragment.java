package hu.adamdobo.onlabproject.myitems;

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

import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.locationservice.MyLocationService;
import hu.adamdobo.onlabproject.model.DeliveryItem;
import hu.adamdobo.onlabproject.model.Item;
import hu.adamdobo.onlabproject.sectionedrecyclerview.SectionHeader;
import hu.adamdobo.onlabproject.sectionedrecyclerview.SectionedRecyclerViewAdapter;

/**
 * Created by Ádám on 4/8/2018.
 */

public class MyItemsFragment extends Fragment implements MyItemsView, OnDeliveryClickedListener{

    private RecyclerView recyclerView;
    private SectionRecyclerViewAdapter myItemsAdapter;
    private List<SectionHeader> sections;
    private MyItemsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new MyItemsPresenterImpl(this, new MyItemsInteractorImpl());
        View contentView = inflater.inflate(R.layout.simple_recyclerview, container, false);
        setRecyclerView(contentView);
        return contentView;
    }

    public static Fragment newInstance() {
        return new MyItemsFragment();
    }

    private void setRecyclerView(View view) {
        setSections();
        recyclerView = view.findViewById(R.id.itemsList);
        myItemsAdapter = new SectionedRecyclerViewAdapter(getActivity(), sections, this);
        recyclerView.setAdapter(myItemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    @Override
    public void setClosedBidsSection() {
        for (Item item :
                presenter.getClosedBids()) {
            myItemsAdapter.insertNewChild(item, 0);
        }
        myItemsAdapter.notifyDataChanged(sections);
    }

    @Override
    public void setOngoingBidsSection() {
        for (Item item :
                presenter.getOngoingBids()) {
            myItemsAdapter.insertNewChild(item, 1);
        }
        myItemsAdapter.notifyDataChanged(sections);
    }

    @Override
    public void setSections() {
        sections = new ArrayList<>();
        sections.add(new SectionHeader(new ArrayList<Item>(), getString(R.string.closed_bids)));
        sections.add(new SectionHeader(new ArrayList<Item>(), getString(R.string.ongoing_bids)));
    }

    @Override
    public void startDeliveryService(DeliveryItem deliveryItem) {
        Intent deliveryIntent = new Intent(getActivity(), MyLocationService.class);
        Bundle bundle = new Bundle();
        bundle.putString("item_id", deliveryItem.ID);
        deliveryIntent.putExtras(bundle);
        getActivity().startService(deliveryIntent);
    }

    @Override
    public void onDeliveryClicked(Item item) {
        Snackbar.make(getView(), "Item delivery started, now it's your responsibility to deliver the item to the winner.", Snackbar.LENGTH_LONG).show();
        presenter.startDelivery(item);
    }
}
