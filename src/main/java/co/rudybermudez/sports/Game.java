package co.rudybermudez.sports;
/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 16, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez.sports
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Game is a model class for all types of possible sporting events.
 */
public class Game {

    /**
     * The home team of a sporting event.
     */
    private final String mHomeTeam;

    /**
     * The away team of a sporting event.
     */
    private final String mAwayTeam;

    /**
     * The game venue of a sporting event.
     */
    private final String mGameVenue;

    /**
     * The game date.
     */
    private final String mGameDate;

    /**
     * The days until the game as type Long.
     */
    private final Long mDaysUntilGame;

    /**
     * The hours until game as type Long. Note: Full days are subtracted from this amount. Its not truly hours until the
     * game. If the game is exactly 72 hours away it will say 3 days 0 hours away.
     */
    private final Long mHoursUntilGame;

    /**
     * The minutes until game as type Long.
     */
    private final Long mMinutesUntilGame;

    /**
     * The seconds until game as type Long.
     */
    private final Long mSecondsUntilGame;

    /**
     * Boolean that represents whether or not the game has started.
     */
    private final Boolean mHasStarted;

    /**
     * The time zone of the user.
     */
    private final String mUserTimeZone;

    /**
     * The string date pattern from the sport score API, used for parsing the time until event.
     */
    private final String mStringDatePattern;

    /**
     * The score of the home team.
     */
    private final Integer mScoreHomeTeam;

    /**
     * The score of the away team.
     */
    private final Integer mScoreAwayTeam;

    /**
     * Instantiates a new Game.
     *
     * @param homeTeam          the name of the home team
     * @param awayTeam          the name of the away team
     * @param gameDate          the game date
     * @param scoreHomeTeam     the score of the home team
     * @param scoreAwayTeam     the score of the away team
     * @param gameVenue         the name of the game venue
     * @param stringDatePattern the string date pattern of the sports score API
     * @param userTimeZone          the time zone of the user
     * @throws ParseException if the string date pattern cannot be parsed correctly
     */
    public Game(String homeTeam, String awayTeam, String gameDate, Integer scoreHomeTeam, Integer scoreAwayTeam, String gameVenue, String stringDatePattern, String userTimeZone) throws ParseException {

        mGameDate = gameDate;
        mScoreHomeTeam = scoreHomeTeam;
        mScoreAwayTeam = scoreAwayTeam;
        mGameVenue = gameVenue;
        mAwayTeam = awayTeam;
        mHomeTeam = homeTeam;
        mStringDatePattern = stringDatePattern;
        mUserTimeZone = userTimeZone;

        final SimpleDateFormat apiFormat = new SimpleDateFormat(mStringDatePattern);
        final Date dateOfGame = apiFormat.parse(mGameDate);
        final Long millis = dateOfGame.getTime() - (Instant.now().atZone(TimeZone.getTimeZone(mUserTimeZone).toZoneId())).toInstant().toEpochMilli();
        mDaysUntilGame = TimeUnit.MILLISECONDS.toDays(millis);
        mHoursUntilGame = TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis));
        mMinutesUntilGame = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        mSecondsUntilGame = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
        mHasStarted = mSecondsUntilGame < 0;
    }

    /**
     * Gets score of the home team.
     *
     * @return the score of the home team
     */
    public Integer getScoreHomeTeam() {
        return mScoreHomeTeam;
    }

    /**
     * Gets score of the away team.
     *
     * @return the score of the  away team
     */
    public Integer getScoreAwayTeam() {
        return mScoreAwayTeam;
    }

    /**
     * Gets the name of the home team.
     *
     * @return the name of the home team
     */
    public String getHomeTeam() {

        return mHomeTeam;
    }

    /**
     * Gets the name of the away team.
     *
     * @return the name of the away team
     */
    public String getAwayTeam() {
        return mAwayTeam;
    }

    /**
     * Gets the name of the game venue.
     *
     * @return the name of the game venue
     */
    public String getGameVenue() {
        return mGameVenue;
    }

    /**
     * Gets the days until game.
     *
     * @return the days until game
     */
    public Integer getDaysUntilGame() {

        return mDaysUntilGame.intValue();
    }

    /**
     * Gets days until game as formatted string. (Today, Tomorrow, In x Days)
     *
     * @return the days until game as string
     */
    public String getDaysUntilGameAsString() {
        if (getDaysUntilGame().equals(0)) {
            return "Today";
        } else if (getDaysUntilGame().equals(1)) {
            return "Tomorrow";
        } else {
            return String.format("In %s days", getDaysUntilGame());
        }
    }

    /**
     * Gets hours until game.
     * Note: Full days are subtracted from this amount. Its not truly hours until the
     * game. If the game is exactly 72 hours away it will say 3 days 0 hours away.
     *
     * @return the hours until game
     */
    public Integer getHoursUntilGame() {
        return mHoursUntilGame.intValue();
    }

    /**
     * Gets minutes until game.
     *
     * @return the minutes until game
     */
    public Integer getMinutesUntilGame() {
        return mMinutesUntilGame.intValue();
    }

    /**
     * Gets seconds until game.
     *
     * @return the seconds until game
     */
    public Integer getSecondsUntilGame() {
        return mSecondsUntilGame.intValue();
    }


    /**
     * Boolean that represents whether or not the game has started.
     *
     * @return the boolean that represents whether or not the game has started.
     */
    public Boolean hasStarted() {
        return mHasStarted;
    }

    /**
     * Gets the time zone of the user.
     *
     * @return the time zone of the user
     */
    public String getUserTimeZone() {
        return mUserTimeZone;
    }
}
