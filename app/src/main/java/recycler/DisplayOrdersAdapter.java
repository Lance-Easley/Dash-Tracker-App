package recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import client.R;
import dataclass.DoorDashOrder;

public class DisplayOrdersAdapter extends RecyclerView.Adapter<DisplayOrdersAdapter.DisplayOrdersViewHolder> {

    private final ArrayList<DoorDashOrder> recyclerOrders;
    private final Context context;
    private final boolean addIds;

    public DisplayOrdersAdapter(Context ct, ArrayList<DoorDashOrder> recyclerOrders, boolean addIds) {
        this.context = ct;
        this.recyclerOrders = recyclerOrders;
        this.addIds = addIds;
    }

    @NonNull
    @Override
    public DisplayOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_card, parent, false);

        return new DisplayOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayOrdersAdapter.DisplayOrdersViewHolder holder, int position) {
        String restaurantName = recyclerOrders.get(position).getName();
        String pay = String.valueOf(recyclerOrders.get(position).getPay());
        String timeOfOrder = recyclerOrders.get(position).getTimeOfOrderString();
        String dayOfWeek = recyclerOrders.get(position).getDayOfWeekString();
        String completeTime = recyclerOrders.get(position).getCompleteTimeString();
        String milesTraveled = String.valueOf(recyclerOrders.get(position).getMilesTraveled());
        String foodPrepPerformance = recyclerOrders.get(position).getFoodPrepPerformanceString();
        String rating = String.valueOf(recyclerOrders.get(position).getRating());

        if (addIds) {
            holder.txtId.setText(String.valueOf(position));
        }

        holder.txtRestaurantName.setText(restaurantName);
        holder.txtPay.setText(pay);
        holder.txtTimeOfOrder.setText(timeOfOrder);
        holder.txtDayOfWeek.setText(dayOfWeek);
        holder.txtCompleteTime.setText(completeTime);
        holder.txtMilesTraveled.setText(milesTraveled);
        holder.txtFoodPrepPerformance.setText(foodPrepPerformance);
        holder.txtRating.setText(rating);
    }

    @Override
    public int getItemCount() { return recyclerOrders.size(); }

    public static class DisplayOrdersViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtId;
        private final TextView txtRestaurantName;
        private final TextView txtPay;
        private final TextView txtTimeOfOrder;
        private final TextView txtDayOfWeek;
        private final TextView txtCompleteTime;
        private final TextView txtMilesTraveled;
        private final TextView txtFoodPrepPerformance;
        private final TextView txtRating;

        public DisplayOrdersViewHolder(final View view) {
            super(view);
            this.txtId = view.findViewById(R.id.order_id_ref);
            this.txtRestaurantName = view.findViewById(R.id.txtRestaurantName);
            this.txtPay = view.findViewById(R.id.txtPay);
            this.txtTimeOfOrder = view.findViewById(R.id.txtTimeOfOrder);
            this.txtDayOfWeek = view.findViewById(R.id.txtDayOfWeek);
            this.txtCompleteTime = view.findViewById(R.id.txtCompleteTime);
            this.txtMilesTraveled = view.findViewById(R.id.txtMilesTraveled);
            this.txtFoodPrepPerformance = view.findViewById(R.id.txtFoodPrepPerformance);
            this.txtRating = view.findViewById(R.id.txtRating);
        }
    }
}


