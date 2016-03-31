package co.rudybermudez.stocks;
/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 17, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez.stocks
 */

/**
 * Stock is a model class for {@link StockReport}.
 */
public class Stock {
    /**
     * The symbol of the stock. EX. AAPL .
     */
    private final String mSymbol;

    /**
     * The stock name.
     */
    private final String mStockName;

    /**
     * The current trade price.
     */
    private final Double mTradePrice;

    /**
     * The point change.
     */
    private final String mPointChange;

    /**
     * Instantiates a new Stock.
     *
     * @param symbol      the symbol
     * @param stockName   the stock name
     * @param tradePrice  the trade price
     * @param pointChange the change in points
     */
    public Stock(String symbol, String stockName, Double tradePrice, String pointChange) {

        mSymbol = symbol;
        mStockName = stockName;
        mTradePrice = tradePrice;
        mPointChange = pointChange;
    }

    /**
     * Returns the stock as a perfectly formatted String.
     *
     * @return A formatted string. Ex. Apple Inc. is trading at $105.92 which is a +0.12 point change from yesterday.
     */
    @Override
    public String toString() {
        return String.format("\n%s is trading at $%s which is a %s point change from yesterday.",
                mStockName,
                mTradePrice,
                mPointChange);
    }

    /**
     * Gets the stock symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return mSymbol;
    }

    /**
     * Gets the stock name.
     *
     * @return the stock name
     */
    public String getStockName() {
        return mStockName;
    }

    /**
     * Gets the current trade price.
     *
     * @return the trade price
     */
    public Double getTradePrice() {
        return mTradePrice;
    }

    /**
     * Gets the change in points.
     *
     * @return the point change
     */
    public String getPointChange() {
        return mPointChange;
    }
}
