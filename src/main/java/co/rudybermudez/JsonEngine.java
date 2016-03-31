package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * The class that facilitates GET requests and downloads JSON.
 */
public class JsonEngine {

    /**
     * The json data as a String.
     */
    private final String mRawJson;

    /**
     * Instantiates a new Json engine.
     *
     * @param jsonData It is best to use method makeConnection as the parameter.
     */
    public JsonEngine(String jsonData) {
        mRawJson = jsonData;
    }

    /**
     * method isThereInternet() is a public static Boolean method that will check if there is an active connection to
     * the Internet.
     *
     * @return returns a boolean. true if there is an active connection to the Internet.
     */
    public static Boolean isThereInternet() {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress("google.com", 80);
        try {
            socket.connect(address, 3000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Make connection to url and stores the body as a string.
     *
     * @param url the url of the site
     * @return json data of the body of the site as type String
     * @throws IOException if the connection to the site is not successful
     */
    public static String makeConnection(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful())
            return response.body().string();
        throw new IOException("Connection to " + url + "was not successful");
    }

    /**
     * Downloads the json weather data from Forecast.io .
     *
     * @param forecastIoApiKey the forecast.io api key as type string
     * @param latitude         the latitude of the location as type double
     * @param longitude        the longitude of the location as type double
     * @param unitHTML         the unit for the Forecast.io api. Ex. Location.Units.IMPERIAL.unitHTML
     * @return the forecast.io json data as type string
     * @throws IOException if the connection to Forecast.io is not successful
     */
    public static String getForecastJSON(String forecastIoApiKey, double latitude, double longitude, String unitHTML) throws IOException {
        String url = String.format("https://api.forecast.io/forecast/%s/%s,%s/?units=%s", forecastIoApiKey, latitude, longitude, unitHTML);
        return makeConnection(url);
    }

    /**
     * Parse simple object. Ex. If the item you want to parse is first level in the json data.
     *
     * @param stringToParse the string to parse
     * @return the object at the string you want to parse
     * @throws JSONException if parsing the string is not successful
     */
    public Object parseSimple(String stringToParse) throws JSONException {
        JSONObject jsonObject = new JSONObject(mRawJson);
        return jsonObject.get(stringToParse);

    }

    /**
     * Gets the root object of the stored json data.
     *
     * @return the root object as type {@link JSONObject}
     * @throws JSONException if the object cannot be parsed
     */
    public JSONObject getObject() throws JSONException {
        return new JSONObject(mRawJson);
    }

    /**
     * Gets the root array of the stored json data.
     *
     * @return the root array as type {@link JSONArray}
     * @throws JSONException if the object cannot be parsed
     */
    public JSONArray getArray() throws JSONException {
        return new JSONArray(mRawJson);
    }

}
