package dataclass;

import java.io.Serializable;

public class PerformanceRating implements Serializable {

    public PerformanceRating(Performance rating) {
        this.rating = rating;
    }

    public enum Performance {
        EARLY, ONPICKUP, LATE, NULL
    }

    private final Performance rating;

    public static Performance parsePerformance(String input) {
        input = input.trim().toLowerCase();
        switch (input.charAt(0)){
            case 'e': { return Performance.EARLY; }
            case 'o': { return Performance.ONPICKUP; }
            case 'l': { return Performance.LATE; }
            default: { return Performance.NULL; }
        }
    }

    // Custom Getter
    public final String getFormattedPerformance() {
        switch (rating) {
            case EARLY: { return "Early"; }
            case ONPICKUP: { return "On Pickup"; }
            case LATE: { return "Late"; }
            default: { return "Null"; }
        }
    }
}
