package co.rudybermudez.stocks;
/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 17, 2016
 * @Project: SmartAlarm
 * @Package:    co.rudybermudez.stocks
 */

import co.rudybermudez.JsonEngine;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import java.io.IOException;

public class StockReport {

    private int mNumOfStocks;
    private Stock[] mStockList;
    private JSONObject mStockJson;

    public StockReport(String tickers) throws JSONException {

        getStockJSON(tickers);
        parseStocksJson();
    }

    private void getStockJSON(String tickers){
        try {

            JsonEngine jsonEngine = new JsonEngine(
                    JsonEngine.makeConnection("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%27"+tickers+"%27)%20&format=json&env=store://datatables.org/alltableswithkeys")
            );
            mStockJson = jsonEngine.getObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
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
    public String printStocks(){
        String allStocks = "Here's the latest from Wall Street, ";
        for (Stock stock : mStockList) {
            allStocks += stock.toString();
        }
        return allStocks;
    }
}
