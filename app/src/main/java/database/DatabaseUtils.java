package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dataclass.DayOfWeek;
import dataclass.DoorDashOrder;
import dataclass.PerformanceRating;
import dataclass.TimeInHMS;

import java.util.ArrayList;

import java.util.HashMap;

public class DatabaseUtils extends SQLiteOpenHelper {
    public DatabaseUtils(Context context) {
        super(context, "doordash_stats.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Orders (" +
                "id INTEGER PRIMARY KEY NOT NULL UNIQUE, " +
                "RestaurantName TEXT NOT NULL, " +
                "Pay DECIMAL NOT NULL, " +
                "TimeOfOrder TIME NOT NULL, " +
                "DayOfWeek TEXT NOT NULL, " +
                "CompleteTime TIME NOT NULL, " +
                "MilesTraveled DECIMAL NOT NULL, " +
                "FoodPrepPerformance TEXT NOT NULL, " +
                "Rating INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE Options (" +
                "    isSentenced            BOOLEAN NOT NULL," +
                "    displayOrderPagination INTEGER NOT NULL," +
                "    searchOrderRows        INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Orders");
        db.execSQL("DROP TABLE IF EXISTS Options");
    }

    public boolean addOrderToDatabase(DoorDashOrder order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("RestaurantName", order.getName());
        contentValues.put("Pay", order.getPay());
        contentValues.put("TimeOfOrder", order.getTimeOfOrderString());
        contentValues.put("DayOfWeek", order.getDayOfWeekString());
        contentValues.put("CompleteTime", order.getCompleteTimeString());
        contentValues.put("MilesTraveled", order.getMilesTraveled());
        contentValues.put("FoodPrepPerformance", order.getFoodPrepPerformanceString());
        contentValues.put("Rating", order.getRating());

        long result = db.insert("Orders", null, contentValues);

        return result != -1;
    }


    public ArrayList<DoorDashOrder> getOrdersFromDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Orders", null);

        ArrayList<DoorDashOrder> result = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(new DoorDashOrder(
                    cursor.getString(1),
                    cursor.getDouble(2),
                    TimeInHMS.parseTime(cursor.getString(3)),
                    new DayOfWeek(DayOfWeek.parseWeekday(cursor.getString(4))),
                    TimeInHMS.parseTime(cursor.getString(5)),
                    cursor.getDouble(6),
                    new PerformanceRating(PerformanceRating.parsePerformance(cursor.getString(7))),
                    cursor.getInt(8)
                ));
            }
        }

        cursor.close();

        return result;
    }

    public ArrayList<Integer> getOrderIdsFromDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Orders", null);

        ArrayList<Integer> result = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(cursor.getInt(0));
            }
        }

        cursor.close();

        return result;
    }

    public HashMap<String, String> getSettingsFromDatabase() {
        HashMap<String, String> settings = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Options", null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            settings.put("isSentenced", (cursor.getString(0).equals("1")) ? "true" : "false");
            settings.put("displayOrderPagination", cursor.getString(1));
            settings.put("searchOrderRows", cursor.getString(2));
        }

        cursor.close();

        return settings;
    }

    public boolean deleteOrderFromDatabase(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete("Orders", "id=?", new String[] {String.valueOf(orderId)});

        return result != -1;
    }

    public ArrayList<String> getRestaurantNames() {
        ArrayList<String> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT RestaurantName FROM Orders", null);

        cursor.moveToFirst();
        result.add(cursor.getString(0));

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(cursor.getString(0));
            }
        }

        cursor.close();

        return result;
    }

//    // Search Functions
    public HashMap<String, String> getNumberOfOrdersByRestaurant(ArrayList<String> restaurants) {
        HashMap<String, String> result = new HashMap<>();

        for (String restaurant : restaurants) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT COUNT(*) AS total FROM Orders WHERE RestaurantName=?", new String[]{restaurant});

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                result.put(restaurant, String.valueOf(cursor.getInt(0)));
            }

            cursor.close();
        }

        System.out.println(result);

        return result;
    }

    public HashMap<String, String> getRatingsByRestaurant(ArrayList<String> restaurants) {
        HashMap<String, String> result = new HashMap<>();

        for (String restaurant : restaurants) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT AVG(Rating) AS average FROM Orders WHERE RestaurantName=?", new String[]{restaurant});

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                result.put(restaurant, String.valueOf(cursor.getDouble(0)));
            }

            cursor.close();
        }

        System.out.println(result);

        return result;
    }

    public HashMap<String, String> getEarningsByRestaurant(ArrayList<String> restaurants) {
        HashMap<String, String> result = new HashMap<>();

        for (String restaurant : restaurants) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT SUM(Pay) AS total FROM Orders WHERE RestaurantName=?", new String[]{restaurant});

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                result.put(restaurant, String.valueOf(cursor.getDouble(0)));
            }

            cursor.close();
        }

        System.out.println(result);

        return result;
    }

    public HashMap<String, String> getOrderCompleteTimesByRestaurant() {
        HashMap<String, String> result = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT RestaurantName, CompleteTime FROM Orders", null);

        cursor.moveToFirst();

        String name = cursor.getString(0);
        String time = cursor.getString(1);

        result.put(name, time);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                name = cursor.getString(0);
                time = cursor.getString(1);

                if (result.containsKey(name)) {
                    int numTime = TimeInHMS.parseTime(time).getNumeratedTime();

                    if (numTime < TimeInHMS.parseTime(result.get(name)).getNumeratedTime()) {
                        result.put(name, time);
                    }
                } else {
                    result.put(name, time);
                }
            }
        }

        cursor.close();

        System.out.println(result);

        return result;
    }

    public HashMap<String, String> getOrdersByHour() {
        HashMap<String, String> result = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT TimeOfOrder FROM Orders", null);

        cursor.moveToFirst();

        TimeInHMS time = TimeInHMS.parseTime(cursor.getString(0)); // Used as hashmap key

        result.put(time.getFormattedHour(), "1");

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                time = TimeInHMS.parseTime(cursor.getString(0)); // Used as hashmap key

                if (result.containsKey(time.getFormattedHour())) {
                    int newVal = Integer.parseInt(result.get(time.getFormattedHour())) + 1;
                    result.put(time.getFormattedHour(), String.valueOf(newVal));
                } else {
                    result.put(time.getFormattedHour(), "1");
                }
            }
        }

        cursor.close();

        System.out.println(result);

        return result;
    }
}
