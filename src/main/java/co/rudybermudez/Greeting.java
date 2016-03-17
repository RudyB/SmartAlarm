package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Greeting {

    public final String goodbye = "That's all for now. Have a good day.";
    private final String mTimezone;
    /**
     * The variable mUsername is of type string and stores the user's first name. It is initialized in the default
     * constructor.
     */
    private final String mUsername;

    /**
     * The default constructor for Greeting takes a string parameter in the format "user_first_name".
     *
     * @param username first name of the user
     * @param timeZone timezone of the location in the config file
     */
    public Greeting(String username, String timeZone) {
        mUsername = username;
        mTimezone = timeZone;
    }

    /**
     * getUsername is a private method that returns the member variable mUsername.
     *
     * @return string with the user's first name
     */
    private String getUsername() {
        return mUsername;
    }

    /**
     * getGreetingIntro() is a private method that will compare the current time to defined hours and return "good
     * morning, good afternoon, good evening". The time is computed based off of the timezone of the location in the
     * config file
     *
     * @return method returns a string in format "Good Morning"
     */
    private String getGreetingIntro() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));
        Integer currentHour = Integer.parseInt(formatter.format(new Date()));
        String intro = "";
        if (currentHour < 12) {
            intro = "Good Morning";
        } else if (currentHour >= 12 && currentHour <= 17) {
            intro = "Good Afternoon";
        } else if (currentHour > 17) {
            intro = "Good Evening";
        }
        return intro;
    }

    /**
     * hello() is a public method that returns the opening message. The time is computed based off of the timezone of
     * the location in the config file.
     *
     * @return method returns a string in the format "Good Morning Rudy, it is Sunday, February 7, 2016 7:42am. Here is
     * what you need to know to start your day."
     */
    public String hello() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));
        String currentTime = formatter.format(new Date());
        return String.format("%s %s, it is %s. Here is what you need to know to start your day.", getGreetingIntro(), getUsername(), currentTime);
    }


}
