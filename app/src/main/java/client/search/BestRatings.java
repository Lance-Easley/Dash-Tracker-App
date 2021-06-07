package client.search;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import client.R;
import database.DatabaseUtils;
import recycler.SearchResultsAdapter;
import statics.Methods;
import statics.Variables;

public class BestRatings extends AppCompatActivity {

    RecyclerView resultView;
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        Variables.dbUtils = new DatabaseUtils(this);
        Methods.updateOrders();

        resultView = findViewById(R.id.resultView);
        title = findViewById(R.id.txtSearchType);

        title.setText(R.string.best_dasher_ratings);

        HashMap<String, String> data = Variables.dbUtils.getRatingsByRestaurant(Variables.dbUtils.getRestaurantNames());
        ArrayList<String[]> sortedData = Methods.getSortedHashMapArray(data);

        SearchResultsAdapter resultAdapter = new SearchResultsAdapter(this, sortedData, false, true);
        resultView.setAdapter(resultAdapter);
        resultView.setLayoutManager(new LinearLayoutManager(this));
    }

    // ActionBar Main Menu Button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_actionbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_menu) {
            Methods.mainActivity(this);
            return true;
        }

        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }
}
