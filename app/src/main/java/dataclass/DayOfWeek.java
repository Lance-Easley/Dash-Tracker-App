package dataclass;

import java.io.Serializable;

public class DayOfWeek implements Serializable {

    public enum Weekday {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, NULL
    }

    private final Weekday day;

    public DayOfWeek(Weekday day) {
        this.day = day;
    }

    public static Weekday parseWeekday(String input) {
        input = input.trim().toLowerCase();

        switch (input.charAt(0)) {
            case 's': {
                switch (input.substring(0, 2)) {
                    case "su": { return Weekday.SUNDAY; }
                    case "sa": { return Weekday.SATURDAY; }
                    default: { return Weekday.NULL; }
                }
            }
            case 't': {
                switch (input.substring(0, 2)) {
                    case "tu": { return Weekday.TUESDAY; }
                    case "th": { return Weekday.THURSDAY; }
                    default: { return Weekday.NULL; }
                }
            }
            case '0': { return Weekday.SUNDAY; }

            case '1':
            case 'm':
                { return Weekday.MONDAY; }

            case '2': { return Weekday.TUESDAY; }

            case '3':
            case 'w':
                { return Weekday.WEDNESDAY; }

            case '4': { return Weekday.THURSDAY; }

            case '5':
            case 'f':
                { return Weekday.FRIDAY; }

            case '6': { return Weekday.SATURDAY; }
        }
        return Weekday.NULL;
    }

    // Custom Getter
    public final String getFormattedDay() {
        switch (day) {
            case SUNDAY: { return "Sunday"; }
            case MONDAY: { return "Monday"; }
            case TUESDAY: { return "Tuesday"; }
            case WEDNESDAY: { return "Wednesday"; }
            case THURSDAY: { return "Thursday"; }
            case FRIDAY: { return "Friday"; }
            case SATURDAY: { return "Saturday"; }
            default: { return "Null"; }
        }
    }
}
