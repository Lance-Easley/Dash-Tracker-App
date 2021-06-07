package client;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import dataclass.DayOfWeek;
import dataclass.DoorDashOrder;
import dataclass.PerformanceRating;
import dataclass.TimeInHMS;
import statics.Methods;
import statics.Variables;

public class RecordOrder extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_order);
    }

    public void btnRecordOrderSubmit(View view) {
        EditText restaurantNameView = findViewById(R.id.editTextRestaurantName);
        EditText payView = findViewById(R.id.editDecimalPay);
        EditText timeOfOrderView = findViewById(R.id.editTimeOrderTime);
        EditText dayOfWeekView = findViewById(R.id.editTextDayOfWeek);
        EditText completeTimeView = findViewById(R.id.editTimeCompleteTime);
        EditText milesTraveledView = findViewById(R.id.editDecimalMiles);
        EditText foodPrepPerformanceView = findViewById(R.id.editTextPrepPerformance);
        RatingBar ratingView = findViewById(R.id.editRating);

        boolean isValid = true;
        String error = "Invalid ";

        String restaurantName = Methods.inputHandler(restaurantNameView.getText().toString(), statics.Prompt.NAME, Variables.orders.size());
        String pay = Methods.inputHandler(payView.getText().toString(), statics.Prompt.PAY, Variables.orders.size());
        String timeOfOrder = Methods.inputHandler(timeOfOrderView.getText().toString(), statics.Prompt.TIMEOFORDER, Variables.orders.size());
        String dayOfWeek = Methods.inputHandler(dayOfWeekView.getText().toString(), statics.Prompt.WEEKDAY, Variables.orders.size());
        String completeTime = Methods.inputHandler(completeTimeView.getText().toString(), statics.Prompt.COMPLETETIME, Variables.orders.size());
        String milesTraveled = Methods.inputHandler(milesTraveledView.getText().toString(), statics.Prompt.MILES, Variables.orders.size());
        String foodPrepPerformance = Methods.inputHandler(foodPrepPerformanceView.getText().toString(), statics.Prompt.FOODPREP, Variables.orders.size());
        String rating = Methods.inputHandler("" + ratingView.getRating(), statics.Prompt.RATING, Variables.orders.size());

        if (restaurantName.equals("fail")){
            error += "Name, ";
            isValid = false;
            restaurantNameView.setText("");
        }
        if (pay.equals("fail")){
            error += "Pay, ";
            isValid = false;
            payView.setText("");
        }
        if (timeOfOrder.equals("fail")){
            error += "Order Time, ";
            isValid = false;
            timeOfOrderView.setText("");
        }
        if (dayOfWeek.equals("fail")){
            error += "Day Of Week, ";
            isValid = false;
            dayOfWeekView.setText("");
        }
        if (completeTime.equals("fail")){
            error += "Complete Time, ";
            isValid = false;
            completeTimeView.setText("");
        }
        if (milesTraveled.equals("fail")){
            error += "Miles, ";
            isValid = false;
            milesTraveledView.setText("");
        }
        if (foodPrepPerformance.equals("fail")){
            error += "Food Prep Performance, ";
            isValid = false;
            foodPrepPerformanceView.setText("");
        }
        if (rating.equals("fail")){
            error += "Rating, ";
            isValid = false;
            ratingView.setRating(0);
        }

        boolean isSuccessful = false;

        if (isValid) {
            isSuccessful = Variables.dbUtils.addOrderToDatabase(new DoorDashOrder(
                            restaurantName,
                            Double.parseDouble(pay),
                            TimeInHMS.parseTime(timeOfOrder),
                            new DayOfWeek(DayOfWeek.parseWeekday(dayOfWeek)),
                            TimeInHMS.parseTime(completeTime),
                            Double.parseDouble(milesTraveled),
                            new PerformanceRating(PerformanceRating.parsePerformance(foodPrepPerformance)),
                            Integer.parseInt(rating)
                    )
            );

            if (isSuccessful) {
                Methods.updateOrders();

                Methods.mainActivity(this);
                Toast.makeText(this, "Order Recorded Successfully", Toast.LENGTH_SHORT).show();
            }
        }

        if (!isValid || !isSuccessful){
            Toast.makeText(this, error.substring(0, error.length() - 2), Toast.LENGTH_LONG).show();
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
