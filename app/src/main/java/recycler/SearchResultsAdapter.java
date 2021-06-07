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

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder> {

    private final ArrayList<String[]> data;
    private final Context context;
    private final boolean showMoneySign;
    private final boolean roundValues;

    public SearchResultsAdapter(Context ct, ArrayList<String[]> data, boolean showMoneySign, boolean roundValues) {
        this.context = ct;
        this.data = data;
        this.showMoneySign = showMoneySign;
        this.roundValues = roundValues;
    }

    @NonNull
    @Override
    public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_card, parent, false);

        return new SearchResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsAdapter.SearchResultsViewHolder holder, int position) {
        String[] row = data.get(position);
        String key = row[0];
        String value = row[1];

        if (roundValues && value.length() > 3) {
            value = value.substring(0, 4);
        }

        holder.txtRank.setText(String.valueOf(position + 1));
        holder.txtKey.setText(key);
        holder.txtValue.setText(value);

        if (showMoneySign) {
            holder.txtValue.setText(String.format("$%s", value));
        }
    }

    @Override
    public int getItemCount() { return data.size(); }

    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtRank;
        private final TextView txtKey;
        private final TextView txtValue;

        public SearchResultsViewHolder(final View view) {
            super(view);
            this.txtRank = view.findViewById(R.id.txtRank);
            this.txtKey = view.findViewById(R.id.txtKey);
            this.txtValue = view.findViewById(R.id.txtValue);
        }
    }
}


