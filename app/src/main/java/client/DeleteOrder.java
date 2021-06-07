package client;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import recycler.DisplayOrdersAdapter;
import statics.Methods;
import statics.Variables;

public class DeleteOrder extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_order);

        recyclerView = findViewById(R.id.recyclerView);

        DisplayOrdersAdapter myAdapter = new DisplayOrdersAdapter(this, Variables.orders, true);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void btnDeleteOrderCard(View view) {
        TextView id = view.findViewById(R.id.order_id_ref);

        if (!id.getText().toString().equals("id")) {
            boolean isSuccess = Variables.dbUtils.deleteOrderFromDatabase(Variables.orderIds.get(Integer.parseInt(id.getText().toString())));
            Methods.updateOrders();

            if (isSuccess) {
                Toast.makeText(this, "Order Deleted Successfully", Toast.LENGTH_SHORT).show();
                Methods.mainActivity(this);
                return;
            }
            Toast.makeText(this, "Order could not be deleted", Toast.LENGTH_SHORT).show();
        }
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
