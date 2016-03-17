package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

import co.rudybermudez.news.NewsReport;
import co.rudybermudez.sports.Soccer;
import co.rudybermudez.weather.Weather;

public class TextCompiler {
    private final Config mConfig;

    public TextCompiler(Config config) {
        mConfig = config;
    }

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

            // Weather
            String weatherTxt = "";
            if (mConfig.getEnableWeather()) {
                String currentWeather;
                String futureWeather;
                Weather weather = new Weather(location);
                currentWeather = (weather.getCurrentWeather());
                futureWeather = (weather.getFutureWeather(1));

                weatherTxt = currentWeather + "\n" + futureWeather;
            }

            // Bitcoin
            String btcTxt = "";
            if (mConfig.getEnableBitcoin())
                btcTxt = (Btc.getCurrentPrice());

            // News
            String newsTxt = "";
            if (mConfig.getEnableNews())
                newsTxt = (new NewsReport().getCurrentStories(mConfig.getNewsSource(), mConfig.getNumberOfStories()));

            // Soccer
            String sportsTxt = "";
            if (mConfig.getEnableSports()) {
                Soccer soccer = new Soccer(location.getTimeZone());
                sportsTxt = soccer.getUpcomingGames() + soccer.getLeagueTable();
            }
            return String.format("%s \n\n%s \n\n%s \n%s%s \n\n%s", helloGreeting, weatherTxt, btcTxt, newsTxt, sportsTxt, goodbyeGreeting);
        }

    }
}
