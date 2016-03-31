package co.rudybermudez.sports;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez.sports
 */

import co.rudybermudez.JsonEngine;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.text.ParseException;


// TODO:rmb 3/23/16 Allow the user to enter the name of a team in the config file. Then search throw all leagues in the api to find the team. Follow respective links to get the league table and next matches.

/**
 * Class that downloads and parses soccer scores. Uses http://api.football-data.org
 */
public class SoccerReport {
    /**
     * The game date pattern of the football-data API.
     */
    private final String mGameDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * The IANA timezone name of the user.
     */
    private final String mUserTimezone;

    /**
     * Instantiates a new SoccerReport.
     *
     * @param userTimezone the user's Timezone
     */
    public SoccerReport(String userTimezone) {
        mUserTimezone = userTimezone;
    }

    /**
     * Init connection json object.
     *
     * @param sUrl the url as String
     * @return the root json object
     * @throws IOException   if unable to connect to url
     * @throws JSONException if json cannot be parsed
     */
    private JSONObject initConnection(String sUrl) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Headers headers = new Headers.Builder()
                .add("X-Auth-Token", "4c4bb9fbbe8a4180892912e58970a596")
                .add("X-Response-Control", "minified")
                .build();
        Request request = new Request.Builder()
                .url(sUrl)
                .headers(headers)
                .build();
        Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string());
    }

    /**
     * Gets the current league table.
     *
     * @return "%s is currently in first place with a lead of %s points over %s."
     * @throws IOException   If the url is inaccessible
     * @throws JSONException if JSON cannot be parsed
     */
    public String getLeagueTable() throws IOException, JSONException {
        JSONObject currentRankings = initConnection("http://api.football-data.org/alpha/soccerseasons/399/leagueTable");
        JSONObject firstPlaceTeam = currentRankings.optJSONArray("standing").optJSONObject(0);
        JSONObject secondPlaceTeam = currentRankings.optJSONArray("standing").optJSONObject(1);


        String firstPlaceName = firstPlaceTeam.optString("teamName");
        Integer firstPlacePoints = firstPlaceTeam.optInt("points");
        String secondPlaceName = secondPlaceTeam.optString("teamName");
        Integer secondPlacePoints = secondPlaceTeam.optInt("points");
        Integer lead = firstPlacePoints - secondPlacePoints;
        return String.format("%s is currently in first place with a lead of %s points over %s.", firstPlaceName, lead.toString(), secondPlaceName);
    }

    /**
     * Gets upcoming games.
     *
     * @return the upcoming games for a team
     * @throws IOException   if the API cannot be reached
     * @throws JSONException if the JSON cannot be parsed
     */
    public String getUpcomingGames() throws IOException, JSONException {
        JSONObject schedule = initConnection("http://api.football-data.org/alpha/teams/81/fixtures?timeFrame=n8");
        JSONArray numOfGames = schedule.getJSONArray("fixtures");
        String games = "";
        if (numOfGames.length() == 0) {
            return "FC Barcelona does not have any games for the next 7 days.\n";
        } else {
            for (int i = 0; i < numOfGames.length(); i++) {
                games += getGameDetails(schedule, i);
            }
            return games;
        }
    }

    /**
     * Gets the stadium name.
     *
     * @param homeTeam the name of the home team
     * @return the stadium name
     */
    private String getStadiumName(String homeTeam) {

        String homeStadium;
        try {
            final String url = "https://api.myjson.com/bins/3vrhh";
            JsonEngine stadium = new JsonEngine(JsonEngine.makeConnection(url));
            homeStadium = stadium.parseSimple(homeTeam).toString();
        } catch (Exception e) {
            homeStadium = homeTeam;
        }
        return homeStadium;
    }

    /**
     * Gets game details for a specific game in the schedule.
     *
     * @param schedule the schedule of upcoming games.
     * @param gameNum  the game number in the schedule
     * @return the game details
     * @throws JSONException if the JSON cannot be parsed
     */
    private String getGameDetails(JSONObject schedule, Integer gameNum) throws JSONException {
        try {
            String homeTeam = schedule.getJSONArray("fixtures").getJSONObject(gameNum).getString("homeTeamName");
            String awayTeam = schedule.getJSONArray("fixtures").getJSONObject(gameNum).getString("awayTeamName");
            String gameDate = schedule.getJSONArray("fixtures").getJSONObject(gameNum).getString("date");
            Integer homeTeamScore = schedule.getJSONArray("fixtures").getJSONObject(gameNum).getJSONObject("result").getInt("goalsHomeTeam");
            Integer awayTeamScore = schedule.getJSONArray("fixtures").getJSONObject(gameNum).getJSONObject("result").getInt("goalsAwayTeam");
            String stadiumName = getStadiumName(homeTeam);

            Game game = new Game(homeTeam, awayTeam, gameDate, homeTeamScore, awayTeamScore, stadiumName, mGameDatePattern, mUserTimezone);
            if (game.hasStarted()) {
                String score = "";
                if (!game.getScoreHomeTeam().equals(-1) && !game.getScoreAwayTeam().equals(-1)) {
                    if (game.getScoreHomeTeam() > game.getScoreAwayTeam()) {
                        score = String.format("%s is currently leading %s, %s - %s.", game.getHomeTeam(), game.getAwayTeam(), game.getScoreHomeTeam(), game.getScoreAwayTeam());
                    } else if (game.getScoreHomeTeam().equals(game.getScoreAwayTeam())) {
                        score = String.format("The score is currently tied, %s - %s.", game.getScoreHomeTeam(), game.getScoreAwayTeam());
                    } else {
                        score = String.format("%s is currently leading %s, %s - %s.", game.getAwayTeam(), game.getHomeTeam(), game.getScoreAwayTeam(), game.getScoreHomeTeam());
                    }
                }
                return String.format("%s is currently playing %s at %s. %s\n", game.getHomeTeam(), game.getAwayTeam(), game.getGameVenue(), score);
            } else {
                return String.format("%s, %s takes on %s at %s.\n", game.getDaysUntilGameAsString(), awayTeam, homeTeam, stadiumName);
            }

        } catch (ParseException e) {
            throw new JSONException("Error: The API for api.football-data.org has changed and the date cannot be parsed. Please notify the developer.");
        }
    }
}
