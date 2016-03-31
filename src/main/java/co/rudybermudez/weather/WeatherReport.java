package co.rudybermudez.weather;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez.weather
 */

import co.rudybermudez.Location;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

/**
 * The class WeatherReport parses Forecast.io json data and creates new instances of {@link Forecast}.
 */
public class WeatherReport {

    /**
     * The Instance of the user's Location.
     */
// TODO:rmb 3/10/16 Check to see if there are alerts in the forecast. If there are, read them out.
    private final Location mLocation;

    /**
     * Instantiates a new WeatherReport.
     *
     * @param location An instance of {@link Location} with the requested location for weather
     */
    public WeatherReport(Location location) {
        mLocation = location;
    }

    /**
     * Imperial wind conditions messages based on wind speeds in mph
     *
     * @return the tree map with wind conditions messages based on wind speeds in mph
     */
    public static TreeMap<Integer, String> ImperialWindMap() {
        TreeMap<Integer, String> windSpeedMap = new TreeMap<>();
        windSpeedMap.put(1, "There is currently a Light Zephyr of %s %s. ");
        windSpeedMap.put(3, "There is currently a light breeze of %s %s. ");
        windSpeedMap.put(7, "There is currently a gentle breeze of %s %s. ");
        windSpeedMap.put(12, "There is currently a moderate breeze of %s %s. ");
        windSpeedMap.put(18, "There is currently a strong breeze of %s %s. ");
        windSpeedMap.put(24, "There is currently a strong wind of %s %s. ");
        windSpeedMap.put(31, "There are currently high winds at %s %s. ");
        windSpeedMap.put(38, "There are currently Gale force winds at %s %s. ");
        windSpeedMap.put(46, "There is currently a strong gale at %s %s. ");
        windSpeedMap.put(55, "There are currently Storm winds at %s %s. ");
        windSpeedMap.put(64, "There are currently Violent storm winds at %s %s. ");
        windSpeedMap.put(73, "There are currently Hurricane force winds at %s %s. ");
        return windSpeedMap;
    }

    /**
     * Metric wind conditions messages based on wind speeds in km/h
     *
     * @return the tree map with wind conditions messages based on wind speeds in km/h
     */
    public static TreeMap<Integer, String> MetricWindMap() {
        TreeMap<Integer, String> windSpeedMap = new TreeMap<>();
        windSpeedMap.put(1, "There is currently a Light Zephyr of %s %s. ");
        windSpeedMap.put(5, "There is currently a light breeze of %s %s. ");
        windSpeedMap.put(11, "There is currently a gentle breeze of %s %s. ");
        windSpeedMap.put(19, "There is currently a moderate breeze of %s %s. ");
        windSpeedMap.put(28, "There is currently a strong breeze of %s %s. ");
        windSpeedMap.put(39, "There is currently a strong wind of %s %s. ");
        windSpeedMap.put(50, "There are currently high winds at %s %s. ");
        windSpeedMap.put(61, "There are currently Gale force winds at %s %s. ");
        windSpeedMap.put(74, "There is currently a strong gale at %s %s. ");
        windSpeedMap.put(88, "There are currently Storm winds at %s %s. ");
        windSpeedMap.put(102, "There are currently Violent storm winds at %s %s. ");
        windSpeedMap.put(117, "There are currently Hurricane force winds at %s %s. ");
        return windSpeedMap;
    }

    /**
     * Parse forecast json forecast.
     *
     * @param jsonData      Forecast.io json data as {@link String}
     * @param daysFromToday amount of days from today (0 is today, 2 is two days from today)
     * @return An instant of {@link Forecast} with the weather data
     * @throws JSONException if JSON cannot be parsed
     */
    private Forecast parseForecastJSON(String jsonData, int daysFromToday) throws JSONException {
        final int MAX_DAYS = 7;
        if (daysFromToday > MAX_DAYS)
            daysFromToday = MAX_DAYS;


        JSONObject forecastJSON = new JSONObject(jsonData);
        JSONObject currently = forecastJSON.getJSONObject("currently");
        JSONObject dailyData = forecastJSON.getJSONObject("daily").getJSONArray("data").getJSONObject(daysFromToday);
        return new Forecast(
                forecastJSON.getString("timezone"),
                currently.getLong("time"),
                currently.getDouble("temperature"),
                currently.getString("summary"),
                currently.getDouble("windSpeed"),
                currently.getDouble("apparentTemperature"),
                dailyData.getString("summary"),
                dailyData.getLong("sunriseTime"),
                dailyData.getLong("sunsetTime"),
                dailyData.getDouble("moonPhase"),
                dailyData.getDouble("precipProbability"),
                dailyData.getDouble("temperatureMin"),
                dailyData.getDouble("temperatureMax")
        );

    }

    /**
     * Gets wind conditions.
     *
     * @param windSpeed the wind speed
     * @param windChill the temperature with wind chill
     * @return the wind conditions as String
     */
    private String getWindConditions(Integer windSpeed, Integer windChill) {
        String windReport;
        String unitSpeed = mLocation.getUnits().unitSpeed;
        String unitTemp = mLocation.getUnits().unitTemperature;
        TreeMap<Integer, String> windSpeedMap = mLocation.getUnits().windSpeedMap;
        Integer month = Integer.parseInt(new SimpleDateFormat("L").format(new Date()));
        String windCondition = "";
        String wind_chill;

        for (Integer speedInterval : windSpeedMap.keySet()) {
            if (windSpeed >= speedInterval)
                windCondition = String.format(windSpeedMap.get(speedInterval), windSpeed, unitSpeed);

        }
        if (windSpeed > 5 && (month < 4 || month > 10)) {
            wind_chill = String.format("With the windchill it feels like %s degrees %s", windChill, unitTemp);
            windReport = windCondition + wind_chill + ".";
        } else {
            windReport = windCondition;
        }


        return windReport;
    }

    /**
     * Gets the current weather.
     *
     * @return An instance of {@link Forecast} with the current weather
     * @throws JSONException if JSON cannot be parsed
     */
    public String getCurrentWeather() throws JSONException {
        Forecast currentForecast = parseForecastJSON(mLocation.getJsonData(), 0);
        String precipitation = "";
        if (currentForecast.getPrecipProbability() > 5) {
            precipitation = String.format("There is a %s percent chance that it is going to rain today.", currentForecast.getPrecipProbability());
        }
        return String.format("Weather today in %s is %s, currently %s degrees %s with a low of %s and a high of %s. %s. %s %s",
                mLocation.getCity(),
                currentForecast.getCurrentSummary(),
                currentForecast.getCurrentTemperature(),
                mLocation.getUnits().unitTemperature,
                currentForecast.getTemperatureMin(),
                currentForecast.getTemperatureMax(),
                currentForecast.getSunriseSunset(),
                getWindConditions(currentForecast.getCurrentWindSpeed(), currentForecast.getApparentTemperature()),
                precipitation);

    }

    /**
     * Gets future weather.
     *
     * @param daysInAdvance the number of days in advance
     * @return An instance of {@link Forecast} with the weather for a given date
     * @throws JSONException if JSON cannot be parsed
     */
    public String getFutureWeather(Integer daysInAdvance) throws JSONException {
        Forecast futureForecast = parseForecastJSON(mLocation.getJsonData(), daysInAdvance);
        String weather;
        if (daysInAdvance.equals(1)) {
            weather = "Tomorrow ";
        } else {
            weather = daysInAdvance.toString() + " days from now ";
        }
        weather = weather + String.format("you can expect %s degrees %s and %s.", futureForecast.getAvgTemperature(), mLocation.getUnits().unitTemperature, futureForecast.getDaySummary());
        return weather;
    }

}
