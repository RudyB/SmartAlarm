package co.rudybermudez.stocks;
/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 17, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez.stocks
 */

import co.rudybermudez.JsonEngine;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import java.io.IOException;

/**
 * The class that downloads and parses Stock report data.
 */
public class StockReport {

    /**
     * The num of stocks the user would like to lookup.
     */
    private int mNumOfStocks;

    /**
     * The list of instances of the class {@link Stock}.
     */
    private Stock[] mStockList;

    /**
     * The stock report json data.
     */
    private JSONObject mStockJson;


    /**
     * Instantiates a new StockReport.
     *
     * @param tickers the tickers the user would like to lookup, separated by commas
     * @throws JSONException if the json data cannot be parsed
     */
    public StockReport(String tickers) throws JSONException {

        getStockJSON(tickers);
        parseStocksJson();
    }

    /**
     * Downloads stock json and stores to mStockJson as {@link JSONObject}.
     *
     * @param tickers the tickers separated by commas
     */
    private void getStockJSON(String tickers) {
        try {

            JsonEngine jsonEngine = new JsonEngine(
                    JsonEngine.makeConnection("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%27" + tickers + "%27)%20&format=json&env=store://datatables.org/alltableswithkeys")
            );
            mStockJson = jsonEngine.getObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse the stocks json data. Stores all the {@link Stock}s in the list mStockList.
     *
     * @throws JSONException the json exception
     */
    private void parseStocksJson() throws JSONException {
        mNumOfStocks = mStockJson.getJSONObject("query").getInt("count");
        Stock[] stockList = new Stock[mNumOfStocks];
        for (int i = 0; i < mNumOfStocks; i++) {
            JSONObject currentStock = mStockJson.getJSONObject("query").getJSONObject("results").getJSONArray("quote").getJSONObject(i);
            stockList[i] = new Stock(
                    currentStock.getString("symbol"),
                    currentStock.getString("Name"),
                    currentStock.getDouble("LastTradePriceOnly"),
                    currentStock.getString("Change")
            );
        }
        mStockList = stockList;

    }

    /**
     * Print the stocks in list mStockList using {@link Stock}.toString() .
     *
     * @return the string
     */
    public String printStocks() {
        String allStocks = "Here's the latest from Wall Street, ";
        for (Stock stock : mStockList) {
            allStocks += stock.toString();
        }
        return allStocks;
    }
}
