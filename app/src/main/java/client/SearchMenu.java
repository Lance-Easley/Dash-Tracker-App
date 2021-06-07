package client;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import client.search.BestHours;
import client.search.BestRatings;
import client.search.FastestOrders;
import client.search.MostMoney;
import client.search.MostOrders;
import statics.Methods;

public class SearchMenu extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_menu);

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

    public void mostOrdersActivity(View view) {
        Intent intent = new Intent(this, MostOrders.class);
        startActivity(intent);
    }

    public void bestRatingsActivity(View view) {
        Intent intent = new Intent(this, BestRatings.class);
        startActivity(intent);
    }

    public void mostMoneyActivity(View view) {
        Intent intent = new Intent(this, MostMoney.class);
        startActivity(intent);
    }

    public void fastestOrdersActivity(View view) {
        Intent intent = new Intent(this, FastestOrders.class);
        startActivity(intent);
    }

    public void bestHoursActivity(View view) {
        Intent intent = new Intent(this, BestHours.class);
        startActivity(intent);
    }
}
