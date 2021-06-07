package dataclass;

import java.io.Serializable;

public class DoorDashOrder implements Serializable {

    private final String name;
    private final double pay;
    private final TimeInHMS timeOfOrder;
    private final DayOfWeek dayOfWeek;
    private final TimeInHMS completeTime;
    private final double milesTraveled;
    private final PerformanceRating foodPrepPerformance;
    private final int rating;

    public DoorDashOrder(String name, double pay, TimeInHMS timeOfOrder, DayOfWeek dayOfWeek, TimeInHMS completeTime, double milesTraveled, PerformanceRating foodPrepPerformance, int rating) {
        this.name = name;
        this.pay = pay;
        this.timeOfOrder = timeOfOrder;
        this.dayOfWeek = dayOfWeek;
        this.completeTime = completeTime;
        this.milesTraveled = milesTraveled;
        this.foodPrepPerformance = foodPrepPerformance;
        this.rating = rating;
    }

    // Custom Getters
    public String getTimeOfOrderString() {
        return timeOfOrder.getFormattedTime();
    }

    public String getDayOfWeekString() {
        return dayOfWeek.getFormattedDay();
    }

    public String getCompleteTimeString() {
        return completeTime.getFormattedTime();
    }

    public String getFoodPrepPerformanceString() {
        return foodPrepPerformance.getFormattedPerformance();
    }

    public String getName() {
        return name;
    }

    public double getPay() {
        return pay;
    }

    public double getMilesTraveled() {
        return milesTraveled;
    }

    public int getRating() {
        return rating;
    }
}
