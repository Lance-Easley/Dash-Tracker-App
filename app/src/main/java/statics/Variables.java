package statics;

import java.util.ArrayList;

import dataclass.DoorDashOrder;
import database.DatabaseUtils;

public class Variables {

    public static ArrayList<DoorDashOrder> orders = new ArrayList<>();
    public static ArrayList<Integer> orderIds = new ArrayList<>();

    public static DatabaseUtils dbUtils;

    public static boolean isSentenced = false;
    public static int orderPagination = 2;
    public static int searchOrderRows = 3;
}
