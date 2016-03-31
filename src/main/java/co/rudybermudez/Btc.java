package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

/**
 * The class that gets the current value of bitcoin according to Coinbase.
 */
public class Btc {

    /**
     * Gets the current value of bitcoin according to Coinbase.
     *
     * @return the current value of bitcoin in USD as String
     * @throws Exception if coinbase cannot be reached
     */
    public static String getCurrentValue() throws Exception {
        String url = "https://api.exchange.coinbase.com/products/BTC-USD/ticker";
        JsonEngine jsonEngine = new JsonEngine(JsonEngine.makeConnection(url));
        return String.format("The current price of bitcoin is $%s.", jsonEngine.parseSimple("price").toString());
    }

}
