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

public class Config {
    private String mUserName;
    private Boolean mEnableWeather;
    private String mWeatherUnits;
    private Boolean mWeatherByIP;
    private String mCityName;
    private String mStateName;
    private Boolean mEnableBitcoin;
    private Boolean mEnableStocks;
    private String mStockNames;
    private Boolean mEnableNews;
    private String mNewsSource;
    private Integer mNumberOfStories;
    private Boolean mEnableSports;
    private String mIvonaSecretKey;
    private String mIvonaAccessKey;
    private Boolean mEnableMusic;
    private String mSongLocation;
    private String mForecastApiKey;


    public Config() throws Exception {
        checkForConfigFile();
        loadProperties();
    }

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
            properties.setProperty("Enable_Sports", "true");
            properties.setProperty("ivonaSecretKey", "secretKey");
            properties.setProperty("ivonaAccessKey", "accessKey");
            properties.setProperty("ForecastIoApiKey", "accessKey");
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
            mEnableSports = Boolean.parseBoolean(properties.getProperty("Enable_Sports"));
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

    private void checkForConfigFile() throws Exception {
        File file = new File("SmartAlarmConfig.properties");
        if (!file.exists()) {
            makeProperties();
            throw new Exception("Please configure the config file and run the application again.");
        }
    }

    private void deleteConfigFile() {
        new File("SmartAlarmConfig.properties").delete();
    }

    public String getUserName() {
        return mUserName;
    }

    public Boolean getEnableWeather() {
        return mEnableWeather;
    }

    public String getWeatherUnits() {
        return mWeatherUnits;
    }

    public Boolean getWeatherByIP() {
        return mWeatherByIP;
    }

    public String getCityName() {
        return mCityName;
    }

    public String getStateName() {
        return mStateName;
    }

    public Boolean getEnableBitcoin() {
        return mEnableBitcoin;
    }

    public Boolean getEnableNews() {
        return mEnableNews;
    }

    public String getNewsSource() {
        return mNewsSource;
    }

    public Integer getNumberOfStories() {
        return mNumberOfStories;
    }

    public Boolean getEnableSports() {
        return mEnableSports;
    }

    public String getIvonaSecretKey() {
        return mIvonaSecretKey;
    }

    public String getIvonaAccessKey() {
        return mIvonaAccessKey;
    }

    public Boolean getEnableMusic() {
        return mEnableMusic;
    }

    public String getSongLocation() {
        return mSongLocation;
    }

    public String getForecastApiKey() {
        return mForecastApiKey;
    }

    public Boolean getEnableStocks() {
        return mEnableStocks;
    }

    public String getStockNames() {
        return mStockNames;
    }

}
