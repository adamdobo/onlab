package hu.adamdobo.onlabproject.myitems;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import hu.adamdobo.onlabproject.model.Item;
import hu.adamdobo.onlabproject.sectionedrecyclerview.SectionHeader;
import hu.adamdobo.onlabproject.sectionedrecyclerview.SectionedRecyclerViewAdapter;

/**
 * Created by Ádám on 4/8/2018.
 */

public class MyItemsFragment extends Fragment implements MyItemsView {

    private RecyclerView recyclerView;
    private SectionRecyclerViewAdapter myItemsAdapter;
    private List<SectionHeader> sections;
    private MyItemsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new MyItemsPresenterImpl(this, new MyItemsInteractorImpl());
        View contentView = inflater.inflate(R.layout.fragment_mybids_myitems, container, false);
        setRecyclerView(contentView);
        return contentView;
    }

    public static Fragment newInstance() {
        return new MyItemsFragment();
    }

    private void setRecyclerView(View view) {
        setSections();
        recyclerView = view.findViewById(R.id.myBidsList);
        myItemsAdapter = new SectionedRecyclerViewAdapter(getActivity(), sections);
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
}
