package co.rudybermudez.news;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez.news
 */

/**
 * Story is a model class for {@link NewsReport}.
 */
public class Story {

    /**
     * The title of a story.
     */
    private final String mTitle;
    /**
     * The abstract (text) of a story.
     */
    private final String mAbstract;

    /**
     * Instantiates a new Story.
     *
     * @param title         the title of the story
     * @param storyAbstract the abstract of the story
     */
    public Story(String title, String storyAbstract) {
        mTitle = title;
        mAbstract = storyAbstract;
    }

    /**
     * Gets the title of the story.
     *
     * @return the title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Gets abstract.
     *
     * @return the abstract
     */
    public String getAbstract() {
        return mAbstract;
    }

    /**
     * A perfectly formatted story.
     *
     * @return a formatted story
     */
    @Override
    public String toString() {
        return this.getTitle() + ".\n" + this.getAbstract() + "\n\n";
    }
}
