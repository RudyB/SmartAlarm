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

public class JsonEngine {

    private final String mRawJson;

    public JsonEngine(String jsonData) {
        mRawJson = jsonData;
    }

    /**
     * method isThereInternet() is a public static Boolean method that will check if there is an active connection to
     * the Internet.
     *
     * @return method returns a boolean. true if there is an active connection to the Internet.
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
            }
        }
    }

    public static String makeConnection(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful())
            return response.body().string();
        throw new IOException("Connection to " + url + "was not sucsessful");
    }

    public static String getForecastJSON(String apiKey, double latitude, double longitude, String unitHTML) throws IOException {
        String url = String.format("https://api.forecast.io/forecast/%s/%s,%s/?units=%s", apiKey, latitude, longitude, unitHTML);
        return makeConnection(url);
    }

    public Object parseSimple(String stringToParse) {
        try {
            JSONObject jsonObject = new JSONObject(mRawJson);
            return jsonObject.get(stringToParse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getObject() {
        try {
            return new JSONObject(mRawJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray getArray() {
        try {
            return new JSONArray(mRawJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
