package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */


import co.rudybermudez.news.NewsReport;
import co.rudybermudez.sports.SoccerReport;
import co.rudybermudez.stocks.StockReport;
import co.rudybermudez.weather.WeatherReport;

/**
 * Class that compiles the text from all user enabled methods.
 */
public class TextCompiler {

    /**
     * mConfig is an instance of the class {@link Config} that gets the user properties from SmartAlarmConfig.properties
     */
    private final Config mConfig;


    /**
     * Default constructor of class TextCompiler. Initializes mConfig.
     *
     * @param config An instance of {@link Config}
     */
    public TextCompiler(Config config) {
        mConfig = config;
    }


    /**
     * soundAlarm() will gather the text output from all of the user enabled methods and then concatenate them all and
     * return the output.
     *
     * @return A string containing all of the text of enabled methods. This is the full output of Smart Alarm
     * @throws Exception if there is no connection to the internet or if user types in unit that does not exist
     */
    public String soundAlarm() throws Exception {
        Location.Units units;
        System.out.println("Pulling Data from the Web... Hold on a bit\n\n");
        if (!JsonEngine.isThereInternet()) {
            throw new Exception("Error: There is no active connection to the internet");
        } else {
            String configUnits = mConfig.getWeatherUnits();
            if (configUnits.toLowerCase().equals("imperial")) {
                units = Location.Units.IMPERIAL;
            } else if (configUnits.toLowerCase().equals("metric")) {
                units = Location.Units.METRIC;
            } else {
                throw new Exception(configUnits + " is not a valid type of units. Please enter 'Imperial' or 'Metric'");
            }

            Location location;
            if (mConfig.getWeatherByIP())
                location = new Location(units, mConfig.getForecastApiKey());
            else {
                location = new Location(mConfig.getCityName(), mConfig.getStateName(), units, mConfig.getForecastApiKey());
            }
            // Greetings
            Greeting greeting = new Greeting(mConfig.getUserName(), location.getTimeZone());
            String helloGreeting = (greeting.hello());
            String goodbyeGreeting = (greeting.goodbye);

            // WeatherReport
            String weatherTxt = "";
            if (mConfig.getEnableWeather()) {
                String currentWeather;
                String futureWeather;
                WeatherReport weatherReport = new WeatherReport(location);
                currentWeather = (weatherReport.getCurrentWeather());
                futureWeather = (weatherReport.getFutureWeather(1));

                weatherTxt = currentWeather + "\n" + futureWeather;
            }

            // Stocks
            String stockTxt = "";
            if (mConfig.getEnableStocks())
                stockTxt = new StockReport(mConfig.getStockNames()).printStocks();

            // Bitcoin
            String btcTxt = "";
            if (mConfig.getEnableBitcoin())
                btcTxt = (Btc.getCurrentValue());

            // News
            String newsTxt = "";
            if (mConfig.getEnableNews())
                newsTxt = (new NewsReport().getCurrentStories(mConfig.getNewsSource(), mConfig.getNumberOfStories()));

            // SoccerReport
            String sportsTxt = "";
            if (mConfig.getEnableSoccer()) {
                SoccerReport soccerReport = new SoccerReport(location.getTimeZone());
                sportsTxt = soccerReport.getUpcomingGames() + soccerReport.getLeagueTable();
            }
            return String.format("%s \n\n%s \n\n%s \n\n%s \n%s%s \n\n%s", helloGreeting, weatherTxt, stockTxt, btcTxt, newsTxt, sportsTxt, goodbyeGreeting);
        }

    }
}
