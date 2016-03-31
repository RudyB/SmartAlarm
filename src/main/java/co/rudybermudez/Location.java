package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

import co.rudybermudez.weather.WeatherReport;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * Class that stores the location, timezone, unit preference, and local weather data of the user.
 */
public class Location {

    /**
     * The type of {@link Units} the user prefers.
     */
    private final Units mUnits;

    /**
     * The Forecast.io api key as a String.
     */
    private final String mForecastIoApiKey;

    /**
     * The name of the user's city as a String.
     */
    private String mCity;

    /**
     * The name of the user's state as a String.
     */
    private String mState;

    /**
     * The latitude coordinate of the user's location as a double.
     */
    private double mLatitude;

    /**
     * The longitude coordinate of the user's location as a double.
     */
    private double mLongitude;

    /**
     * The user's timezone based on location as a String.
     */
    private String mTimeZone;

    /**
     * The json data of the weather report based on the user's location as a String.
     */
    private String mJsonData;

    /**
     * Instantiates a new Location using geo-location.
     *
     * @param units            the {@link Units} that the user prefers. (IMPERIAL or METRIC)
     * @param forecastIoApiKey the forecast.io api key
     */
    public Location(Units units, String forecastIoApiKey) {
        mForecastIoApiKey = forecastIoApiKey;
        mUnits = units;
        geoLocation();
    }

    /**
     * Instantiates a new Location by manually entering the city name and state name.
     *
     * @param cityName         the city name of the user as a String
     * @param stateName        the state name of the user as a String
     * @param units            the {@link Units} that the user prefers. (IMPERIAL or METRIC)
     * @param forecastIoApiKey the forecast.io api key
     */
    public Location(String cityName, String stateName, Units units, String forecastIoApiKey) {
        mForecastIoApiKey = forecastIoApiKey;
        mUnits = units;
        mCity = cityName;
        mState = stateName;
        getLatitudeLongitude();
        loadTimezone();
    }

    /**
     * Gets the user's preferred type of units as type {@link Units}.
     *
     * @return The user's preferred type of units as type {@link Units}.
     */
    public Units getUnits() {
        return mUnits;
    }

    /**
     * Gets the forecast.io api key.
     *
     * @return The string forecast.io api key
     */
    public String getForecastIoApiKey() {
        return mForecastIoApiKey;
    }

    /**
     * Gets the user's city name.
     *
     * @return A string containing the name of the user's city.
     */
    public String getCity() {
        return mCity;
    }


    /**
     * Gets the user's state.
     *
     * @return the state as a String
     */
    public String getState() {
        return mState;
    }


    /**
     * Gets the user's latitude.
     *
     * @return the latitude as type double.
     */
    public double getLatitude() {
        return mLatitude;
    }


    /**
     * Gets the json data of the weather at the user's defined location..
     *
     * @return the Forecast.io json data as type string
     */
    public String getJsonData() {
        return mJsonData;
    }


    /**
     * Gets the user's longitude.
     *
     * @return the user's longitude as type double.
     */
    public double getLongitude() {
        return mLongitude;
    }


    /**
     * Gets the user's time zone.
     *
     * @return the user's time zone as type String
     */
    public String getTimeZone() {
        return mTimeZone;
    }


    /**
     * Method that will get the city name, state name, latitude, and longitude of the user by referencing their IP
     * address.
     */
    private void geoLocation() {
        final String url = "http://ipinfo.io/json";
        try {
            JsonEngine jsonEngine = new JsonEngine(JsonEngine.makeConnection(url));

            String city = jsonEngine.getObject().getString("city");
            String state = jsonEngine.getObject().getString("region");

            Boolean cityAndStateValid = !city.isEmpty() && !state.isEmpty();
            if (cityAndStateValid) {
                mCity = city;
                mState = state;
                List<String> longlat = Arrays.asList(jsonEngine.getObject().getString("loc").split(","));
                mLatitude = Double.parseDouble(longlat.get(0));
                mLongitude = Double.parseDouble(longlat.get(1));
                loadTimezone();
            } else {
                throw new IOException("ERROR: Could not determine your location based off of you IP. Please manually enter your location");
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the latitude and longitude by referencing the user's city and state name.
     */
    private void getLatitudeLongitude() {
        try {
            String url = "https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where woeid in (select woeid from geo.places(1) where text = '"+ mCity +"," + mState +"') & format=json & env=store datatables.org alltableswithkeys";
            JsonEngine connection = new JsonEngine(JsonEngine.makeConnection(url));
            JSONObject item = connection.getObject().getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item");
            mLatitude = item.getDouble("lat");
            mLongitude = item.getDouble("long");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading latitude and longitude");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads the user's timezone.
     */
    private void loadTimezone() {
        try {
            mJsonData = JsonEngine.getForecastJSON(mForecastIoApiKey, mLatitude, mLongitude, mUnits.unitHTML);
            mTimeZone = new JSONObject(mJsonData).getString("timezone");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * An enum for Units.
     */
    public enum Units {
        /**
         * Imperial units.
         */
        IMPERIAL("fahrenheit", "MPH", "us", WeatherReport.ImperialWindMap()),
        /**
         * Metric units.
         */
        METRIC("celsius", "km/h", "si", WeatherReport.MetricWindMap());

        /**
         * The Unit for temperature.
         */
        public final String unitTemperature;
        /**
         * The Unit for speed.
         */
        public final String unitSpeed;
        /**
         * The Unit type of the Forecast.IO Api.
         */
        public final String unitHTML;
        /**
         * The Wind speed map for the respected unit.
         */
        public final TreeMap<Integer, String> windSpeedMap;


        /**
         * Instantiates a new enum Units.
         *
         * @param unitTemperature the unit for temperature
         * @param unitSpeed       the unit for speed
         * @param unitHTML        the unit for the Forecast.io api
         * @param windSpeedMap    the wind speed map for the respected unit
         */
        Units(String unitTemperature, String unitSpeed, String unitHTML, TreeMap<Integer, String> windSpeedMap) {
            this.unitTemperature = unitTemperature;
            this.unitSpeed = unitSpeed;
            this.unitHTML = unitHTML;
            this.windSpeedMap = windSpeedMap;
        }
    }
}