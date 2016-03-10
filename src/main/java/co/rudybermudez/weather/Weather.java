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

public class Weather {

    // TODO:rmb 3/10/16 Check to see if there are alerts in the forecast. If there are, read them out.
    private Location mLocation;

    public Weather(Location location) throws Exception {
        mLocation = location;
    }

    private Forecast parseForecastJSON(String jsonData, int daysFromToday) {
        final int MAX_DAYS = 7;
        if (daysFromToday > MAX_DAYS)
            daysFromToday = MAX_DAYS;

        try {
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String getWindConditions(Integer windSpeed, Integer windChill) {
        String windReport = "";
        String unitSpeed = mLocation.getUnits().unitSpeed;
        String unitTemp = mLocation.getUnits().unitTemperature;
        Integer month = Integer.parseInt(new SimpleDateFormat("L").format(new Date()));
        String windCondition = "";
        String wind_chill;

        // TODO:rmb 3/10/16 Create enum. Enum stores HashSet. HashSet for each unit. Create for loop.
        // If windSpeed > preset value, continue. When it is so longer larger, stop and get message.

        if (mLocation.getUnits().unitSpeed.equals("MPH")) {
            if (!windSpeed.toString().isEmpty()) {
                if (windSpeed < 1)
                    windCondition = "The wind is calm.";
                if (windSpeed > 1)
                    windCondition = "There is currently a Light Zephyr.";
                if (windSpeed > 3)
                    windCondition = String.format("There is currently a light breeze of %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 7)
                    windCondition = String.format("There is currently a gentle breeze of %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 12)
                    windCondition = String.format("There is currently a moderate breeze of %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 18)
                    windCondition = String.format("There is currently a strong breeze of %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 24)
                    windCondition = String.format("There is currently a strong wind of %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 31)
                    windCondition = String.format("There are currently high winds at %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 38)
                    windCondition = String.format("There are currently Gale force winds at %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 46)
                    windCondition = String.format("There is currently a strong gale at %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 55)
                    windCondition = String.format("There are currently Storm winds at %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 64)
                    windCondition = String.format("There are currently Violent storm winds at %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 73)
                    windCondition = String.format("There are curerntly Hurricane force winds at %s %s. ", windSpeed, unitSpeed);
            }
        } else {
            if (!windSpeed.toString().isEmpty()) {
                if (windSpeed < 1)
                    windCondition = "The wind is calm.";
                if (windSpeed > 1)
                    windCondition = "There is currently a Light Zephyr.";
                if (windSpeed > 5)
                    windCondition = String.format("There is currently a light breeze of %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 11)
                    windCondition = String.format("There is currently a gentle breeze of %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 19)
                    windCondition = String.format("There is currently a moderate breeze of %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 28)
                    windCondition = String.format("There is currently a strong breeze of %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 39)
                    windCondition = String.format("There is currently a strong wind of %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 50)
                    windCondition = String.format("There are currently high winds at %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 61)
                    windCondition = String.format("There are currently Gale force winds at %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 74)
                    windCondition = String.format("There is currently a strong gale at %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 88)
                    windCondition = String.format("There are currently Storm winds at %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 102)
                    windCondition = String.format("There are currently Violent storm winds at %s %s. ", windSpeed, unitSpeed);
                if (windSpeed > 117)
                    windCondition = String.format("There are curerntly Hurricane force winds at %s %s. ", windSpeed, unitSpeed);
            }

        }
        if (windSpeed > 5 && (month < 4 || month > 10)) {
            wind_chill = String.format("With the windchill it feels like %s degrees %s", windChill, unitTemp);
            windReport = windCondition + wind_chill + ".";
        }


        return windReport;
    }


    public String getCurrentWeather() {
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

    public String getFutureWeather(Integer daysInAdvance) {
        Forecast futureForecast = parseForecastJSON(mLocation.getJsonData(), daysInAdvance);
        String weather;
        if (daysInAdvance.equals(1)) {
            weather = "Tomorrow ";
        } else {
            weather = daysInAdvance.toString() + " days from now ";
        }
        weather = weather + String.format("you can expect %s degrees %s and %s.", futureForecast.getAvgTemperature(), mLocation.getUnits().unitTemperature, futureForecast.getSummary());
        return weather;
    }


}
