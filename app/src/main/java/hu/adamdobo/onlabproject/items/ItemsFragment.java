package hu.adamdobo.onlabproject.items;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.dialog.NewItemDialog;
import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/13/2018.
 */

public class ItemsFragment extends Fragment implements ItemsView, SaveItemCallbackListener{
    private ItemsPresenter presenter;
    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;
    private FrameLayout progressBarHolder;

    @Override
    public void onDestroy(){
        presenter.onDestroy();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new ItemsPresenterImpl(this, new ItemsInteractorImpl());
        getActivity().setTitle(getString(R.string.items));
        View contentView = inflater.inflate(R.layout.fragment_items, container, false);
        progressBarHolder = contentView.findViewById(R.id.progressBarHolder);
        setRecyclerView(contentView);
        FloatingActionButton fab = (FloatingActionButton) contentView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewItemDialog newItemDialog = NewItemDialog.newInstance(ItemsFragment.this);
                newItemDialog.show(getActivity().getSupportFragmentManager(), NewItemDialog.TAG);
            }
        });
        return contentView;
    }

    private void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.itemList);
        itemsAdapter = new ItemsAdapter(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext().getApplicationContext(), 2));
        recyclerView.setAdapter(itemsAdapter);
    }

    public static Fragment newInstance() {
        return new ItemsFragment();
    }


    @Override
    public void saveItemWithPicture(Item item, byte[] bytes) {
        presenter.saveItemWithPicture(item, bytes);
    }

    @Override
    public void onItemChanged() {
        itemsAdapter.changeItem(presenter.getChangedItem());
    }

    @Override
    public void onItemAdded() {
        itemsAdapter.addItem(presenter.getAddedItem());
    }

    @Override
    public void onItemDeleted() {
        itemsAdapter.deleteItem(presenter.getDeletedItem());
    }

    @Override
    public void hideProgress() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
    }

}