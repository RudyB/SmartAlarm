package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

public class Btc {

    public static String getCurrentPrice() throws Exception {
        String url = "https://api.exchange.coinbase.com/products/BTC-USD/ticker";
        JsonEngine jsonEngine = new JsonEngine(JsonEngine.makeConnection(url));
        return String.format("The current price of bitcoin is $%s.", jsonEngine.parseSimple("price").toString());
    }

}
