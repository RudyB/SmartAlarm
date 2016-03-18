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

public class Game {
    private final String mHomeTeam;
    private final String mAwayTeam;
    private final String mGameVenue;
    private final String mGameDate;
    private final Long mDaysUntilGame;
    private final Long mHoursUntilGame;
    private final Long mMinutesUntilGame;
    private final Long mSecondsUntilGame;
    private final Boolean mHasStarted;
    private final String mTimeZone;
    private final String mStringDatePattern;
    private final Integer mScoreHomeTeam;
    private final Integer mScoreAwayTeam;

    public Game(String homeTeam, String awayTeam, String gameDate, Integer scoreHomeTeam, Integer scoreAwayTeam, String gameVenue, String stringDatePattern, String timeZone) throws ParseException {

        mGameDate = gameDate;
        mScoreHomeTeam = scoreHomeTeam;
        mScoreAwayTeam = scoreAwayTeam;
        mGameVenue = gameVenue;
        mAwayTeam = awayTeam;
        mHomeTeam = homeTeam;
        mStringDatePattern = stringDatePattern;
        mTimeZone = timeZone;

        final SimpleDateFormat apiFormat = new SimpleDateFormat(mStringDatePattern);
        final Date dateOfGame = apiFormat.parse(mGameDate);
        final Long millis = dateOfGame.getTime() - (Instant.now().atZone(TimeZone.getTimeZone(mTimeZone).toZoneId())).toInstant().toEpochMilli();
        mDaysUntilGame = TimeUnit.MILLISECONDS.toDays(millis);
        mHoursUntilGame = TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis));
        mMinutesUntilGame = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        mSecondsUntilGame = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
        mHasStarted = mSecondsUntilGame < 0;
    }

    public Integer getScoreHomeTeam() {
        return mScoreHomeTeam;
    }

    public Integer getScoreAwayTeam() {
        return mScoreAwayTeam;
    }

    public String getHomeTeam() {

        return mHomeTeam;
    }

    public String getAwayTeam() {
        return mAwayTeam;
    }

    public String getGameVenue() {
        return mGameVenue;
    }

    public Integer getDaysUntilGame() {

        return mDaysUntilGame.intValue();
    }

    public String getDaysUntilGameAsString() {
        if (getDaysUntilGame().equals(0)) {
            return "Today";
        } else if (getDaysUntilGame().equals(1)) {
            return "Tomorrow";
        } else {
            return String.format("In %s days", getDaysUntilGame());
        }
    }

    public Integer getHoursUntilGame() {
        return mHoursUntilGame.intValue();
    }

    public Integer getMinutesUntilGame() {
        return mMinutesUntilGame.intValue();
    }

    public Integer getSecondsUntilGame() {
        return mSecondsUntilGame.intValue();
    }

    public Boolean hasStarted() {
        return mHasStarted;
    }

    public String getTimeZone() {
        return mTimeZone;
    }
}
