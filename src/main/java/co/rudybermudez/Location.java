package co.rudybermudez;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Location {

    private final Units mUnits;
    private final String mApiKey;
    private String mCity;
    private String mState;
    private double mLatitude;
    private double mLongitude;
    private String mTimeZone;
    private String mJsonData;


    public Location(Units units, String apiKey) {
        mApiKey = apiKey;
        mUnits = units;
        geoLocation();
    }

    public Location(String cityName, String stateName, Units units, String apiKey) {
        mApiKey = apiKey;
        mUnits = units;
        mCity = cityName;
        mState = stateName;
        getLatitudeLongitude();
        loadTimezone();
    }

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
                List<String> longlat = Arrays.asList(jsonEngine.getObject().optString("loc").split(","));
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

    private void getLatitudeLongitude() {
        try {
            JsonEngine connection = new JsonEngine(JsonEngine.makeConnection("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + saltLocationInput(mCity) + "%2C" + saltLocationInput(mState) + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys"));
            JSONObject item = connection.getObject().optJSONObject("query").optJSONObject("results").optJSONObject("channel").optJSONObject("item");
            mLatitude = item.optDouble("lat");
            mLongitude = item.optDouble("long");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading latitude and longitude");
        }

    }

    private void loadTimezone() {
        try {
            mJsonData = JsonEngine.getForecastJSON(mApiKey, mLatitude, mLongitude, mUnits.unitHTML);
            mTimeZone = new JSONObject(mJsonData).getString("timezone");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;

    }

    public String getJsonData() {
        return mJsonData;
    }

    public void setJsonData(String jsonData) {
        mJsonData = jsonData;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    private String saltLocationInput(String locationName) {
        if (locationName.contains(" ")) {
            String[] arrCity = locationName.split(" ");
            String saltedCity = "";
            for (String string : arrCity) {

                saltedCity += string;
            }
            return saltedCity;
        } else {
            return locationName;
        }
    }

    public enum Units {
        IMPERIAL("fahrenheit", "MPH", "us"),
        METRIC("celsius", "km/h", "si");

        public final String unitTemperature;
        public final String unitSpeed;
        public final String unitHTML;

        Units(String unitTemperature, String unitSpeed, String unitHTML) {
            this.unitTemperature = unitTemperature;
            this.unitSpeed = unitSpeed;
            this.unitHTML = unitHTML;
        }
    }
    }