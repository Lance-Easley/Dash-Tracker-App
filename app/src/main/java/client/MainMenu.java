package client;

import database.DatabaseUtils;
import statics.Methods;
import statics.Variables;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Variables.dbUtils = new DatabaseUtils(this);

        Methods.updateOrders();
        Methods.updateSettings();
    }

    public void recordOrdersActivity(View view) {
        Intent intent = new Intent(this, RecordOrder.class);
        startActivity(intent);
    }

    public void showOrdersActivity(View view) {
        if (Variables.orders.size() > 0) {
            Intent intent = new Intent(this, ShowOrders.class);
            startActivity(intent);
            return;
        }

        Toast.makeText(this, "You have no recorded orders", Toast.LENGTH_SHORT).show();
    }

    public void deleteOrderActivity(View view) {
        if (Variables.orders.size() > 0) {
            Intent intent = new Intent(this, DeleteOrder.class);
            startActivity(intent);
            return;
        }

        Toast.makeText(this, "You have no recorded orders", Toast.LENGTH_SHORT).show();
    }

    public void searchOrdersActivity(View view) {
        if (Variables.orders.size() > 0) {
            Intent intent = new Intent(this, SearchMenu.class);
            startActivity(intent);
            return;
        }

        Toast.makeText(this, "You have no recorded orders", Toast.LENGTH_SHORT).show();
    }
}
