package hu.adamdobo.onlabproject.delivery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.map.MapsActivity;
import hu.adamdobo.onlabproject.model.DeliveryItem;

/**
 * Created by Ádám on 4/11/2018.
 */

class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ItemsViewHolder> {
    private List<DeliveryItem> itemList;
    private Context context;
    private OnDeliveryCloseListener deliveryCloseListener;

    public DeliveryAdapter(Context context, OnDeliveryCloseListener deliveryCloseListener) {
        this.context = context;
        this.deliveryCloseListener = deliveryCloseListener;
        itemList = new ArrayList<>();
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delivery_itemlist, parent, false);
        return new ItemsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        DeliveryItem deliveryItem = itemList.get(position);
        holder.itemName.setText(deliveryItem.name);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItems(List<DeliveryItem> deliveries) {
        itemList.clear();
        itemList.addAll(deliveries);
        notifyDataSetChanged();
    }



    public DeliveryItem getItem(int pos) {
        return itemList.get(pos);
    }

    public void deleteItem(DeliveryItem deliveryItem) {
        int index = -1;
        if(deliveryItem != null) {
            for (DeliveryItem item : itemList) {
                if (item.ID.equals(deliveryItem.ID)) {
                    index = itemList.indexOf(item);
                }
            }
        }
        if(index!=-1){
            itemList.remove(index);
            notifyItemRemoved(index);
        }
    }

    public interface OnDeliveryCloseListener {
        void onDeliveryCloseClicked(View view, int pos);
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView doneImageView;

        public ItemsViewHolder(final View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.deliveryItemName);
            doneImageView = itemView.findViewById(R.id.doneImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent(context, MapsActivity.class);
                    mapIntent.putExtra("item_id", itemList.get(getAdapterPosition()).ID);
                    mapIntent.putExtra("latitude", itemList.get(getAdapterPosition()).latitude);
                    mapIntent.putExtra("longitude", itemList.get(getAdapterPosition()).longitude);
                    context.startActivity(mapIntent);
                }
            });
            doneImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deliveryCloseListener.onDeliveryCloseClicked(v, getAdapterPosition());
                }
            });
        }
    }
}
