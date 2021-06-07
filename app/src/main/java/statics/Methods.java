package statics;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import client.MainMenu;
import dataclass.TimeInHMS;

public class Methods {

    public static void updateOrders() {
        Variables.orders = Variables.dbUtils.getOrdersFromDatabase();
        Variables.orderIds = Variables.dbUtils.getOrderIdsFromDatabase();
    }

    public static void updateSettings() {
        HashMap<String, String> settings = Variables.dbUtils.getSettingsFromDatabase();

        String newIsSentenced = settings.get("isSentenced");
        String newOrderPagination = settings.get("displayOrderPagination");
        String newSearchOrderRows = settings.get("searchOrderRows");

        if (newIsSentenced != null) {
            Variables.isSentenced = Boolean.parseBoolean(newIsSentenced);
        }

        if (newOrderPagination != null) {
            Variables.orderPagination = Integer.parseInt(newOrderPagination);
        }

        if (newSearchOrderRows != null) {
            Variables.searchOrderRows = Integer.parseInt(newSearchOrderRows);
        }
    }

    public static void mainActivity(Context context) {
        Intent intent = new Intent(context, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static String inputHandler(String input, statics.Prompt prompt, int sizeOfOrdersArray) {
        String userInput = input.trim().toLowerCase();

        if (prompt == statics.Prompt.NEXTPAGE && userInput.length() == 0) {
            return "nextPage";
        } else if (userInput.length() < 1) {
            if (prompt == statics.Prompt.PAY) {
                System.out.print("Invalid Input, Try Again > $");
                return "fail";
            }

            System.out.print("Invalid Input, Try Again > ");

            return "fail";
        }

        if (userInput.equals("...")) {
            return "...";
        }

        // anywhere you see `break` signifies invalid input, prompting a retry
        // similarly, a `return` signifies a valid input, returning a string value
        // if a `break` or `return` is not reached, a retry is prompted.
        switch (prompt) {
            case NAME: { return uppercaseName(userInput); }
            case PAY:
            case MILES:
            {
                if (isNumeric(userInput) && Double.parseDouble(userInput) >= 0) return userInput;
                break;
            }
            case RATING: {
                if (!isNumeric(userInput)) break;
                if (!isWholeNumber(userInput)) break;

                int rating = (int) Double.parseDouble(userInput);

                if (rating < 6 && rating > 0) return "" + rating;

                break;
            }
            case ENTRIES: {
                if (isNumeric(userInput)) {
                    if (isWholeNumber(userInput) && Integer.parseInt(userInput) > 0) return userInput;
                }

                break;
            }
            case TIMEOFORDER: {
                StringBuilder result = new StringBuilder();
                String[] times = userInput.split(":", 10);

                if (arrayContains(times, "") || isArrayNotNumeric(times) || isInvalidArrayTimes(times)) break;

                switch (times.length) {
                    case 2: { // hour and minutes provided
                        for (int i = 0; i < 2; i++) {
                            if (times[i].length() == 2) result.append(times[i]).append(":");
                            else if (times[i].length() == 1) result.append("0").append(times[i]).append(":");
                        }

                        result.append("00");

                        return result.toString();
                    }
                    case 3: { // hour, minutes, and seconds provided
                        for (int i = 0; i < 3; i++) {
                            if (times[i].length() == 2) result.append(times[i]).append(":");
                            else if (times[i].length() == 1) result.append("0").append(times[i]).append(":");
                        }

                        return result.toString();
                    }
                }

                break;
            }
            case COMPLETETIME: {
                StringBuilder result = new StringBuilder();
                String[] times = userInput.split(":", 0);

                if (arrayContains(times, "") || isArrayNotNumeric(times) || isInvalidArrayTimes(times)) break;

                switch (times.length) {
                    case 1: { // minutes provided
                        result.append("00:");
                        if (times[0].length() == 2) result.append(times[0]).append(":00");
                        else if (times[0].length() == 1) result.append("0").append(times[0]).append(":00");
                        break;
                    }
                    case 2: { // hour and minutes provided
                        for (int i = 0; i < 2; i++) {
                            if (times[i].length() == 2) result.append(times[i]).append(":");
                            else if (times[i].length() == 1) result.append("0").append(times[i]).append(":");
                        }

                        result.append("00");
                        break;
                    }
                    case 3: { // hour, minutes, and seconds provided
                        for (int i = 0; i < 3; i++) {
                            if (times[i].length() == 2) result.append(times[i]).append(":");
                            else if (times[i].length() == 1) result.append("0").append(times[i]).append(":");
                        }
                        break;
                    }
                }

                return result.toString();
            }
            case WEEKDAY: {
                if (isNumeric(userInput) && !isWholeNumber(userInput)) break;

                switch (userInput.charAt(0)) {
                    case 's': {
                        if (userInput.length() < 2) break;

                        switch (userInput.substring(0, 2)) {
                            case "su": { return "Sunday"; }
                            case "sa": { return "Saturday"; }
                        }

                        break;
                    }
                    case 't': {
                        if (userInput.length() < 2) break;

                        switch (userInput.substring(0, 2)) {
                            case "tu": { return "Tuesday"; }
                            case "th": { return "Thursday"; }
                        }

                        break;
                    }
                    case '0': { return "Sunday"; }
                    case '1':
                    case 'm':
                    { return "Monday"; }
                    case '2': { return "Tuesday"; }
                    case '3':
                    case 'w':
                    { return "Wednesday"; }
                    case '4': { return "Thursday"; }
                    case '5':
                    case 'f':
                    { return "Friday"; }
                    case '6': { return "Saturday"; }
                }

                break;
            }
            case FOODPREP: {
                switch (userInput.charAt(0)){
                    case 'e': { return "Early"; }
                    case 'o': { return "OnPickup"; }
                    case 'l': { return "Late"; }
                }

                break;
            }
            case MAINMENU: {
                switch (userInput.charAt(0)) {
                    case 's': {
                        if (userInput.length() < 2) break;

                        switch (userInput.substring(0, 2)) {
                            case "sh": { return "showOrders"; }
                            case "se": { return "searchOrders"; }
                        }

                        break;
                    }
                    case 'r':
                    case '1': { return "recordOrder"; }
                    case '2': { return "showOrders"; }
                    case '3': { return "searchOrders"; }
                    case 'd':
                    case '4':
                    { return "deleteOrder"; }
                    case 'o':
                    case '5':
                    { return "options"; }
                    case 'q':
                    case '6':
                    { return "quit"; }
                }

                break;
            }
            case NEXTPAGE: {
                if (userInput.charAt(0) == 'q') return "quit";

                break;
            }
            case DELETEORDER: {
                if (isNumeric(userInput) && isWholeNumber(userInput)) {
                    if (Integer.parseInt(userInput) <= sizeOfOrdersArray && Integer.parseInt(userInput) > 0) {
                        return userInput;
                    }
                }

                break;
            }
            case SEARCHMENU: {
                switch (userInput.charAt(0)) {
                    case 'o':
                    case '1':
                    { return "mostOrders"; }
                    case 'b':
                    case '2':
                    { return "bestRatings"; }
                    case 'm':
                    case '3':
                    { return "mostMoney"; }
                    case 'h':
                    case '4':
                    { return "bestHours"; }
                    case 'f':
                    case '5':
                    { return "fastestRestaurants"; }
                    case 'c':
                    case '6':
                    { return "cancel"; }
                }

                break;
            }
            case OPTIONS: {
                switch (userInput.charAt(0)) {
                    case 'o':
                    case '1':
                    { return "orderDisplayFormat"; }
                    case 'd':
                    case '2':
                    { return "orderPagination"; }
                    case 's':
                    case '3':
                    { return "searchOrderRows"; }
                    case 'a':
                    case '4':
                    { return "accept"; }
                    case 'c':
                    case '5':
                    { return "cancel"; }
                }

                break;
            }
        }

        return "fail";
    }

    // Smaller QOL Functions
    public static String uppercaseString(String input) {
        return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static String uppercaseName(String input) {
        String[] words = input.split(" ", 0);
        StringBuilder result = new StringBuilder();

        for (String s : words) result.append(uppercaseString(s)).append(" ");

        return result.toString().trim();
    }

    public static boolean isNumeric(String str) {
        if (str == null) return false;

        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean isWholeNumber(String str) {
        return Double.parseDouble(str) % 1 == 0;
    }

    public static boolean isArrayNotNumeric(String[] array) {
        for (String s : array) {
            if (!isNumeric(s)) return true;
        }

        return false;
    }

    public static boolean arrayContains(String[] array, String target) {
        for (String s : array) {
            if (s.equals(target)) return true;
        }

        return false;
    }

    public static boolean isInvalidArrayTimes(String[] array) {
        // Assuming that the array contains number strings
        if (array.length == 1) {
            return !isWholeNumber(array[0]) || (Integer.parseInt(array[0]) < 0 || Integer.parseInt(array[0]) >= 60);
        }

        for (int i = 0; i < array.length; i++) {
            if (!isWholeNumber(array[i])) return true;
            if (i == 0) {
                if (Integer.parseInt(array[i]) < 0 || Integer.parseInt(array[i]) > 23) {
                    return true;
                }
            }
            if (Integer.parseInt(array[i]) < 0 || Integer.parseInt(array[i]) > 59) return true;
        }

        return false;
    }

    public static String getKeyWithHighestValue(HashMap<String, String> dict) {
        String maxKey = "";
        double maxVal = 0.0;

        for (String key : dict.keySet()) {
            if (Double.parseDouble(Objects.requireNonNull(dict.get(key))) > maxVal) {
                maxVal = Double.parseDouble(Objects.requireNonNull(dict.get(key)));
                maxKey = key;
            }
        }

        return maxKey;
    }

    public static String getKeyWithLowestTime(HashMap<String, String> dict) {
        String minKey = "";
        int minVal = 999999999;

        for (String key : dict.keySet()) {
            int time = TimeInHMS.parseTime(Objects.requireNonNull(dict.get(key))).getNumeratedTime();
            if (time < minVal) {
                minVal = time;
                minKey = key;
            }
        }

        return minKey;
    }

    public static ArrayList<String[]> getSortedHashMapArray(HashMap<String, String> input) {
        ArrayList<String[]> result = new ArrayList<>();

        int size = input.size();

        for (int i = 0; i < size; i++) {
            String key = getKeyWithHighestValue(input);

            result.add(new String[]{key, input.get(key)});

            input.remove(key);
        }

        System.out.println(result);

        return result;
    }

    public static ArrayList<String[]> getSortedTimeHashMapArray(HashMap<String, String> input) {
        ArrayList<String[]> result = new ArrayList<>();

        int size = input.size();

        for (int i = 0; i < size; i++) {
            String key = getKeyWithLowestTime(input);

            result.add(new String[]{key, input.get(key)});

            input.remove(key);
        }

        System.out.println(result);

        return result;
    }
}
