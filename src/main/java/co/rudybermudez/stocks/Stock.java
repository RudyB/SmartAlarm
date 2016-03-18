package co.rudybermudez.stocks;
/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 17, 2016
 * @Project: SmartAlarm
 * @Package:    co.rudybermudez.stocks
 */

public class Stock {
    private final String mSymbol;
    private final String mStockName;
    private final Double mTradePrice;
    private final String mPercentChange;

    @Override
    public String toString() {
        return String.format("\n%s is trading at $%s which is a %s point change from yesterday.",
                mStockName,
                mTradePrice,
                mPercentChange);
    }

    public Stock(String symbol, String stockName, Double tradePrice, String percentChange) {

        mSymbol = symbol;
        mStockName = stockName;
        mTradePrice = tradePrice;
        mPercentChange = percentChange;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public String getStockName() {
        return mStockName;
    }

    public Double getTradePrice() {
        return mTradePrice;
    }

    public String getPercentChange() {
        return mPercentChange;
    }
}
