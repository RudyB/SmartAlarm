package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

import java.io.*;
import java.util.Properties;

/**
 * The class that creates a configuration file for the smart alarm and loads the user defined settings.
 */
public class Config {
    /**
     * The user's name.
     */
    private String mUserName;

    /**
     * The boolean to enable weather.
     */
    private Boolean mEnableWeather;

    /**
     * The desired weather units.
     */
    private String mWeatherUnits;

    /**
     * The boolean to enable geo-location
     */
    private Boolean mWeatherByIP;

    /**
     * The city name.
     */
    private String mCityName;

    /**
     * The state name.
     */
    private String mStateName;

    /**
     * The boolean to enable bitcoin.
     */
    private Boolean mEnableBitcoin;

    /**
     * The boolean to enable stocks.
     */
    private Boolean mEnableStocks;

    /**
     * The String of stock names separated by commas.
     */
    private String mStockNames;

    /**
     * The boolean to enable news.
     */
    private Boolean mEnableNews;

    /**
     * The news source (BBC or NYT).
     */
    private String mNewsSource;

    /**
     * The user defined number of stories.
     */
    private Integer mNumberOfStories;

    /**
     * The boolean to enable soccer.
     */
    private Boolean mEnableSoccer;

    /**
     * The ivona secret key.
     */
    private String mIvonaSecretKey;

    /**
     * The ivona access key.
     */
    private String mIvonaAccessKey;

    /**
     * The boolean to enable music.
     */
    private Boolean mEnableMusic;

    /**
     * The file-path of the song the user would like to play.
     */
    private String mSongLocation;

    /**
     * The forecast.io api key.
     */
    private String mForecastApiKey;


    /**
     * Instantiates a new Config. Checks if the config file.
     *
     * @throws Exception if the config file does not exist.
     */
    public Config() throws Exception {
        checkForConfigFile();
    }

    /**
     * Makes the properties file SmartAlarmConfig.properties .
     */
    private void makeProperties() {
        Properties properties = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("SmartAlarmConfig.properties");
            //Set Default Properties
            properties.setProperty("Your_First_Name", "Bob");
            properties.setProperty("Enable_Weather", "true");
            properties.setProperty("Weather_Units", "Imperial");
            properties.setProperty("Get_Weather_Location_from_IP", "true");
            properties.setProperty("City_Name", "Las Vegas");
            properties.setProperty("State_Name", "Nevada");
            properties.setProperty("Get_Bitcoin_Exchange_Rate", "true");
            properties.setProperty("Enable_Stocks", "true");
            properties.setProperty("Stock_Names", "AAPL,GOOG,TSLA");
            properties.setProperty("Enable_News", "true");
            properties.setProperty("News_Source", "BBC");
            properties.setProperty("Number_of_News_Stories", "4");
            properties.setProperty("Enable_Soccer", "true");
            properties.setProperty("ivonaSecretKey", "secretKey");
            properties.setProperty("ivonaAccessKey", "accessKey");
            properties.setProperty("ForecastIoApiKey", "apiKey");
            properties.setProperty("playSong", "false");
            properties.setProperty("songLocation", "Had to Hear.mp3");
            properties.store(output, "Smart Alarm Configuration");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    /**
     * Loads properties from file SmartAlarmConfig.properties and makes properties accessible by getters.
     */
    private void loadProperties() {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("SmartAlarmConfig.properties");
            // Load Properties
            properties.load(input);
            // Get Property Values and store them to Hash Map
            mUserName = properties.getProperty("Your_First_Name");
            mForecastApiKey = properties.getProperty("ForecastIoApiKey");
            mEnableWeather = Boolean.parseBoolean(properties.getProperty("Enable_Weather"));

            if (mEnableWeather) {
                mWeatherUnits = properties.getProperty("Weather_Units");
                mWeatherByIP = Boolean.parseBoolean(properties.getProperty("Get_Weather_Location_from_IP"));
                if (!mWeatherByIP) {
                    mCityName = properties.getProperty("City_Name");
                    mStateName = properties.getProperty("State_Name");
                }
            }

            mEnableStocks = Boolean.parseBoolean(properties.getProperty("Enable_Stocks"));
            if (mEnableStocks) {
                mStockNames = properties.getProperty("Stock_Names");
            }

            mEnableBitcoin = Boolean.parseBoolean(properties.getProperty("Get_Bitcoin_Exchange_Rate"));
            mEnableNews = Boolean.parseBoolean(properties.getProperty("Enable_News"));
            if (mEnableNews) {
                mNewsSource = properties.getProperty("News_Source");
                mNumberOfStories = Integer.parseInt(properties.getProperty("Number_of_News_Stories"));
            }
            mEnableSoccer = Boolean.parseBoolean(properties.getProperty("Enable_Soccer"));
            mIvonaAccessKey = properties.getProperty("ivonaAccessKey");
            mIvonaSecretKey = properties.getProperty("ivonaSecretKey");
            mEnableMusic = Boolean.parseBoolean(properties.getProperty("playSong"));
            mSongLocation = properties.getProperty("songLocation");

        } catch (IOException ioe) {
            ioe.printStackTrace();
            deleteConfigFile();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    /**
     * Checks for config file. If the file exists, the config file is loaded. If the file does not exist, exception is
     * thrown.
     *
     * @throws Exception if config file does not exist.
     */
    private void checkForConfigFile() throws Exception {
        File file = new File("SmartAlarmConfig.properties");
        if (!file.exists()) {
            makeProperties();
            throw new Exception("Please configure the config file and run the application again.");
        } else {
            loadProperties();
        }
    }

    /**
     * Delete config file.
     */
    private void deleteConfigFile() {
        new File("SmartAlarmConfig.properties").delete();
    }

    /**
     * Gets user name.
     *
     * @return the user's name
     */
    public String getUserName() {
        return mUserName;
    }

    /**
     * Checks if weather is enabled.
     *
     * @return boolean that represents whether weather is enabled
     */
    public Boolean getEnableWeather() {
        return mEnableWeather;
    }

    /**
     * Gets weather units.
     *
     * @return the weather units
     */
    public String getWeatherUnits() {
        return mWeatherUnits;
    }

    /**
     * Determines if geo-location is enabled
     *
     * @return the boolean representing whether geo-location is enabled
     */
    public Boolean getWeatherByIP() {
        return mWeatherByIP;
    }

    /**
     * Gets city name.
     *
     * @return the city name
     */
    public String getCityName() {
        return mCityName;
    }

    /**
     * Gets state name.
     *
     * @return the state name
     */
    public String getStateName() {
        return mStateName;
    }

    /**
     * Determines if bitcoin is enabled.
     *
     * @return the boolean representing whether bitcoin is enabled
     */
    public Boolean getEnableBitcoin() {
        return mEnableBitcoin;
    }

    /**
     * Determines if news report is enabled.
     *
     * @return the boolean representing whether news is enabled
     */
    public Boolean getEnableNews() {
        return mEnableNews;
    }

    /**
     * Gets news source.
     *
     * @return the news source
     */
    public String getNewsSource() {
        return mNewsSource;
    }

    /**
     * Gets the number of stories.
     *
     * @return the number of stories
     */
    public Integer getNumberOfStories() {
        return mNumberOfStories;
    }

    /**
     * Determines if soccer is enabled.
     *
     * @return the boolean representing whether soccer is enabled
     */
    public Boolean getEnableSoccer() {
        return mEnableSoccer;
    }

    /**
     * Gets the  ivona secret key.
     *
     * @return the ivona secret key
     */
    public String getIvonaSecretKey() {
        return mIvonaSecretKey;
    }

    /**
     * Gets the ivona access key.
     *
     * @return the ivona access key
     */
    public String getIvonaAccessKey() {
        return mIvonaAccessKey;
    }

    /**
     * Determines if music is enabled.
     *
     * @return the boolean representing whether soccer is enabled
     */
    public Boolean getEnableMusic() {
        return mEnableMusic;
    }

    /**
     * Gets the file-path of the song the user would like to play.
     *
     * @return the file-path of the song
     */
    public String getSongLocation() {
        return mSongLocation;
    }

    /**
     * Gets the forecast.io api key.
     *
     * @return the forecast.io api key
     */
    public String getForecastApiKey() {
        return mForecastApiKey;
    }

    /**
     * Determines if stocks are enabled.
     *
     * @return the boolean representing whether stocks are enabled
     */
    public Boolean getEnableStocks() {
        return mEnableStocks;
    }

    /**
     * Gets the user defined stock names.
     *
     * @return the stock names the user would like in the report
     */
    public String getStockNames() {
        return mStockNames;
    }

}
