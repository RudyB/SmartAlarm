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

/**
 * The class Forecast is a model class for {@link WeatherReport}.
 */
public class Forecast {
    // TODO:rmb 3/10/16 Add alerts

    /**
     * The user's timezone. The IANA timezone name for the requested location.
     */
    private final String mTimezone;

    /**
     * The current UNIX time at time of request.
     */
    private final long mTime;

    /**
     * The current temperature.
     */
    private final double mCurrentTemperature;

    /**
     * A human-readable text summary of the current weather.
     */
    private final String mCurrentSummary;

    /**
     * The M current wind speed.
     */
    private final double mCurrentWindSpeed;

    /**
     * The apparent temperature "feels-like" (includes wind chill).
     */
    private final double mApparentTemperature;

    /**
     * The summary for the day (Different from mCurrentSummary)
     */
    private final String mDaySummary;

    /**
     * The UNIX time of sunrise at location.
     */
    private final long mSunriseTime;

    /**
     * The UNIX time of sunset at location.
     */
    private final long mSunsetTime;

    /**
     * The current condition of the moon phase. A number representing the fractional part of the lunation number of the
     * given day. This can be thought of as the “percentage complete” of the current lunar month: a value of 0
     * represents a new moon, a value of 0.25 represents a first quarter moon, a value of 0.5 represents a full moon,
     * and a value of 0.75 represents a last quarter moon. (The ranges in between these represent waxing crescent,
     * waxing gibbous, waning gibbous, and waning crescent moons, respectively.)
     */
    private final double mMoonPhase;

    /**
     * A numerical value between 0 and 1 (inclusive) representing the probability of precipitation occurring at the
     * given time.
     */
    private final double mPrecipProbability;

    /**
     * Numerical value representing the minimum apparent temperatures for the date.
     */
    private final double mTemperatureMin;

    /**
     * Numerical value representing the maximum apparent temperatures for the date.
     */
    private final double mTemperatureMax;

    /**
     * Instantiates a new Forecast.
     *
     * @param timezone            The IANA timezone name for the requested location.
     * @param time                the time in UNIX
     * @param currentTemperature  the current temperature
     * @param currentSummary      the current weather summary
     * @param currentWindSpeed    the current wind speed
     * @param apparentTemperature the apparent temperature
     * @param daySummary          the daySummary
     * @param sunriseTime         the sunrise time in UNIX
     * @param sunsetTime          the sunset time in UNIX
     * @param moonPhase           the moon phase
     * @param precipProbability   the precip probability
     * @param temperatureMin      the temperature min
     * @param temperatureMax      the temperature max
     */
    public Forecast(String timezone, long time, double currentTemperature, String currentSummary, double currentWindSpeed, double apparentTemperature, String daySummary, long sunriseTime, long sunsetTime, double moonPhase, double precipProbability, double temperatureMin, double temperatureMax) {
        mTimezone = timezone;
        mTime = time;
        mCurrentTemperature = currentTemperature;
        mCurrentSummary = currentSummary;
        mCurrentWindSpeed = currentWindSpeed;
        mApparentTemperature = apparentTemperature;
        mDaySummary = daySummary;
        mSunriseTime = sunriseTime;
        mSunsetTime = sunsetTime;
        mMoonPhase = moonPhase;
        mPrecipProbability = precipProbability;
        mTemperatureMin = temperatureMin;
        mTemperatureMax = temperatureMax;
    }

    /**
     * Gets the apparent temperature. The "feels-like" temperature.
     *
     * @return the apparent temperature
     */
    public Integer getApparentTemperature() {
        return (int) Math.round(mApparentTemperature);
    }

    /**
     * Gets the IANA timezone name for the requested location.
     *
     * @return the timezone
     */
    public String getTimezone() {
        return mTimezone;
    }

    /**
     * Gets the UNIX time at request.
     *
     * @return the time
     */
    public long getTime() {
        return mTime;
    }

    /**
     * Gets current temperature.
     *
     * @return the current temperature
     */
    public Integer getCurrentTemperature() {
        return (int) Math.round(mCurrentTemperature);
    }

    /**
     * Gets a human-readable text summary of the current weather.
     *
     * @return the current summary
     */
    public String getCurrentSummary() {
        return mCurrentSummary;
    }

    /**
     * Gets current wind speed.
     *
     * @return the current wind speed
     */
    public Integer getCurrentWindSpeed() {
        return (int) Math.round(mCurrentWindSpeed);
    }

    /**
     * Gets a human-readable text summary of the weather for the whole day. (Note: Different from getCurrentSummary)
     *
     * @return the summary
     */
    public String getDaySummary() {
        return mDaySummary;
    }

    /**
     * Gets sunrise time in UNIX.
     *
     * @return the sunrise time
     */
    public long getSunriseTime() {
        return mSunriseTime;
    }

    /**
     * Gets sunset time in UNIX.
     *
     * @return the sunset time
     */
    public long getSunsetTime() {
        return mSunsetTime;
    }

    /**
     * Gets the current condition of the moon phase.
     * <p>
     * A number representing the fractional part of the lunation number of the given day. This can be thought of as the
     * “percentage complete” of the current lunar month: a value of 0 represents a new moon, a value of 0.25 represents
     * a first quarter moon, a value of 0.5 represents a full moon, and a value of 0.75 represents a last quarter moon.
     * (The ranges in between these represent waxing crescent, waxing gibbous, waning gibbous, and waning crescent
     * moons, respectively.)
     *
     * @return the moon phase
     */
    public double getMoonPhase() {
        return mMoonPhase;
    }

    /**
     * Gets precip probability.
     * <p>
     * A numerical value between 0 and 1 (inclusive) representing the probability of precipitation occurring at the
     * given time.
     *
     * @return the precip probability
     */
    public Integer getPrecipProbability() {
        return (int) Math.round(mPrecipProbability);
    }

    /**
     * Gets the numerical value representing the minimum apparent temperatures for the date.
     *
     * @return the temperature min
     */
    public Integer getTemperatureMin() {
        return (int) Math.round(mTemperatureMin);
    }

    /**
     * Gets the numerical value representing the maximum apparent temperatures for the date.
     *
     * @return the temperature max
     */
    public Integer getTemperatureMax() {
        return (int) Math.round(mTemperatureMax);
    }

    /**
     * Gets the average temperature for the date.
     *
     * @return the avg temperature
     */
    public Integer getAvgTemperature() {
        return (getTemperatureMax() + getTemperatureMin()) / 2;
    }

    // TODO:rmb 3/10/16 This can be simplified

    /**
     * Format sunrise or sunset time to string as h:mm a.
     *
     * @param option {@link SunriseSunset}.SUNSET or {@link SunriseSunset}.SUNRISE
     * @return the string with the time of option
     */
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

    /**
     * Gets sunrise message.
     *
     * @return the formatted sunrise
     */
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

    /**
     * Gets sunset message.
     *
     * @return the formatted sunset
     */
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

    /**
     * Gets the sunrise and sunset.
     *
     * @return A string with the sunrise and sunset
     */
    public String getSunriseSunset() {
        return getFormattedSunrise() + getFormattedSunset();
    }

    /**
     * The enum SunriseSunset are options for method FormatSunriseSunset in {@link Forecast}.
     */
    private enum SunriseSunset {
        /**
         * Sunrise option.
         */
        SUNRISE,
        /**
         * Sunset option.
         */
        SUNSET
    }
}
