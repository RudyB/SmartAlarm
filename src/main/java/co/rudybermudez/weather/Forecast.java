package co.rudybermudez.weather;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez.weather
 */

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class Forecast {
    // TODO:rmb 3/10/16 Add alerts
    private final String mTimezone;
    private final long mTime;
    private final double mCurrentTemperature;
    private final String mCurrentSummary;
    private final double mCurrentWindSpeed;
    private final double mApparentTemperature;
    private final String mSummary;
    private final long mSunriseTime;
    private final long mSunsetTime;
    private final double mMoonPhase;
    private final double mPrecipProbability;
    private final double mTemperatureMin;
    private final double mTemperatureMax;

    public Forecast(String timezone, long time, double currentTemperature, String currentSummary, double currentWindSpeed, double apparentTemperature, String summary, long sunriseTime, long sunsetTime, double moonPhase, double precipProbability, double temperatureMin, double temperatureMax) {
        mTimezone = timezone;
        mTime = time;
        mCurrentTemperature = currentTemperature;
        mCurrentSummary = currentSummary;
        mCurrentWindSpeed = currentWindSpeed;
        mApparentTemperature = apparentTemperature;
        mSummary = summary;
        mSunriseTime = sunriseTime;
        mSunsetTime = sunsetTime;
        mMoonPhase = moonPhase;
        mPrecipProbability = precipProbability;
        mTemperatureMin = temperatureMin;
        mTemperatureMax = temperatureMax;
    }

    public Integer getApparentTemperature() {
        return (int) Math.round(mApparentTemperature);
    }

    public String getTimezone() {
        return mTimezone;
    }

    public long getTime() {
        return mTime;
    }

    public Integer getCurrentTemperature() {
        return (int) Math.round(mCurrentTemperature);
    }

    public String getCurrentSummary() {
        return mCurrentSummary;
    }

    public Integer getCurrentWindSpeed() {
        return (int) Math.round(mCurrentWindSpeed);
    }

    public String getSummary() {
        return mSummary;
    }

    public long getSunriseTime() {
        return mSunriseTime;
    }

    public long getSunsetTime() {
        return mSunsetTime;
    }

    public double getMoonPhase() {
        return mMoonPhase;
    }

    public Integer getPrecipProbability() {
        return (int) Math.round(mPrecipProbability);
    }

    public Integer getTemperatureMin() {
        return (int) Math.round(mTemperatureMin);
    }

    public Integer getTemperatureMax() {
        return (int) Math.round(mTemperatureMax);
    }

    public Integer getAvgTemperature() {
        return (getTemperatureMax() + getTemperatureMin()) / 2;
    }

    // TODO:rmb 3/10/16 This can be simplified

    private String FormatSunriseSunset(SunriseSunset option) {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimezone()));

        Date dateTime;
        if (option.equals(SunriseSunset.SUNRISE)) {
            dateTime = new Date(getSunriseTime() * 1000);
        } else {
            dateTime = new Date(getSunsetTime() * 1000);
        }
        return formatter.format(dateTime);

    }

    private String getFormattedSunrise() {
        String sunriseTime = FormatSunriseSunset(SunriseSunset.SUNRISE);
        String sunrise;

        long now = Instant.now().atZone(TimeZone.getTimeZone(mTimezone).toZoneId()).toEpochSecond();

        long lSunrise = Instant.ofEpochSecond(mSunriseTime).atZone(TimeZone.getTimeZone(mTimezone).toZoneId()).toEpochSecond();

        if (now < lSunrise) {
            sunrise = String.format("Sunrise will be at %s ", sunriseTime);
        } else {
            sunrise = String.format("Sunrise was at %s ", sunriseTime);
        }
        return sunrise;
    }

    private String getFormattedSunset() {
        String sunsetTime = FormatSunriseSunset(SunriseSunset.SUNSET);
        String sunset;

        long now = Instant.now().atZone(TimeZone.getTimeZone(mTimezone).toZoneId()).toEpochSecond();

        long lSunset = Instant.ofEpochSecond(mSunsetTime).atZone(TimeZone.getTimeZone(mTimezone).toZoneId()).toEpochSecond();

        if (now < lSunset) {
            sunset = String.format("and sunset will be at %s", sunsetTime);
        } else {
            sunset = String.format("and sunset was at %s", sunsetTime);
        }
        return sunset;
    }

    public String getSunriseSunset() {
        return getFormattedSunrise() + getFormattedSunset();
    }

    private enum SunriseSunset {
        SUNRISE,
        SUNSET,
    }
}
