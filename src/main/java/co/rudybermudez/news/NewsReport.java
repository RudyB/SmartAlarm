package co.rudybermudez.news;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez.news
 */

import co.rudybermudez.JsonEngine;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import de.vogella.rss.model.Feed;
import de.vogella.rss.model.FeedMessage;
import de.vogella.rss.read.RSSFeedParser;

import java.io.IOException;
import java.util.ArrayList;


/**
 * The class that parses News and returns it as a String
 */
public class NewsReport {

    /**
     * Parses New York Times stories.
     *
     * @param storyNumber the story number
     * @return An instance of class {@link Story}
     * @throws JSONException if the json cannot be parsed from the URL
     * @throws IOException   if the NYT api cannot be reached
     */
    private Story nytStory(Integer storyNumber) throws JSONException, IOException {
        final String url = "http://api.nytimes.com/svc/topstories/v1/home.json?api-key=6e5464ab5a4a70ce98d2a9a9ebe9f6c3:0:73465657";
        JsonEngine nyt = new JsonEngine(JsonEngine.makeConnection(url));
        JSONObject story = nyt.getObject().getJSONArray("results").getJSONObject(storyNumber);
        String storyTitle = story.getString("title");
        String storyAbstract = story.getString("abstract");
        return new Story(storyTitle, storyAbstract);
    }

    /**
     * Parses BBC stories.
     *
     * @param storyNumber the story number
     * @return An instance of class {@link Story}
     */
    private Story bbcStory(Integer storyNumber) {
        RSSFeedParser parser = new RSSFeedParser("http://feeds.bbci.co.uk/news/world/rss.xml");
        Feed feed = parser.readFeed();
        FeedMessage bbcStory = feed.getMessages().get(storyNumber);
        String storyTitle = bbcStory.getTitle();
        String storyAbstract = bbcStory.getDescription();
        return new Story(storyTitle, storyAbstract);
    }


    /**
     * Validates the entered news source and then calls the respected methods to return a concatenated string of news
     * reports dependant on the parameter numOfStories.
     *
     * @param newsSource   the news source
     * @param numOfStories the num of stories
     * @return A concatenated string of all the stories
     * @throws Exception if the parameter newsSource is not valid. Ex. it is not (NYT or BBC)
     */
    public String getCurrentStories(String newsSource, Integer numOfStories) throws Exception {
        String newsReport = "";
        ArrayList<String> possibleNewsSources = new ArrayList<>();
        possibleNewsSources.add("nyt");
        possibleNewsSources.add("bbc");
        String listOfPossibleNewsSources = "";
        if (!possibleNewsSources.contains(newsSource.toLowerCase())) {
            for (String listOfNewsSources : possibleNewsSources) {
                listOfPossibleNewsSources = listOfNewsSources + ", " + listOfPossibleNewsSources;
            }
            throw new Exception("Error: " + newsSource + " is not a valid option for a news source. Try " + listOfPossibleNewsSources);

        } else if (newsSource.toLowerCase().equals("nyt")) {
            newsReport = "\nAnd now, The latest stories from the front page of the New York Times.\n\n";
            for (int i = 0; i < numOfStories; i++) {
                newsReport += nytStory(i).toString();
            }
        } else if (newsSource.toLowerCase().equals("bbc")) {
            newsReport = "\nAnd now, The latest stories from the World section of the BBC News.\n\n";
            for (int i = 0; i < numOfStories; i++) {
                newsReport += bbcStory(i).toString();
            }
        }
        return newsReport;
    }

}
