package hu.adamdobo.onlabproject.mybids;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.model.Item;
import hu.adamdobo.onlabproject.sectionedrecyclerview.SectionHeader;
import hu.adamdobo.onlabproject.sectionedrecyclerview.SectionedRecyclerViewAdapter;

/**
 * Created by Ádám on 4/6/2018.
 */

public class MyBidsFragment extends Fragment implements MyBidsView {
    private MyBidsPresenter presenter;
    private RecyclerView recyclerView;
    private SectionedRecyclerViewAdapter myBidsAdapter;
    private List<SectionHeader> sections;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new MyBidsPresenterImpl(this, new MyBidsInteractorImpl());
        getActivity().setTitle(getString(R.string.my_bids));
        View contentView = inflater.inflate(R.layout.simple_recyclerview, container, false);
        setRecyclerView(contentView);
        return contentView;
    }


    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public static Fragment newInstance() {
        return new MyBidsFragment();
    }

    private void setRecyclerView(View view) {
        setSections();
        recyclerView = view.findViewById(R.id.itemsList);
        myBidsAdapter = new SectionedRecyclerViewAdapter(getActivity(), sections, null);
        recyclerView.setAdapter(myBidsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    @Override
    public void setWonItemsSection() {
        for (Item item :
                presenter.getWonItems()) {
           myBidsAdapter.insertNewChild(item, 0);
        }
        myBidsAdapter.notifyDataChanged(sections);
    }

    @Override
    public void setOngoingBidsSection() {
        for (Item item :
                presenter.getOngoingBids()) {
            myBidsAdapter.insertNewChild(item, 1);
        }
        myBidsAdapter.notifyDataChanged(sections);
    }

    @Override
    public void setSections() {
        sections = new ArrayList<>();
        sections.add(new SectionHeader(new ArrayList<Item>(), getString(R.string.items_won)));
        sections.add(new SectionHeader(new ArrayList<Item>(), getString(R.string.ongoing_bids)));
    }
}
