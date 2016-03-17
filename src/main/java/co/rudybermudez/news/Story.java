package co.rudybermudez.news;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package:    co.rudybermudez.news
 */

public class Story {

    private final String mTitle;
    private final String mAbstract;

    public Story(String title, String anAbstract) {
        mTitle = title;
        mAbstract = anAbstract;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAbstract() {
        return mAbstract;
    }

    @Override
    public String toString() {
        return mTitle + ".\n" + mAbstract + "\n\n";
    }
}
