package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

import javazoom.jl.player.Player;

import java.io.FileInputStream;


/**
 * This class loads mp3 files and plays them.
 */
public class PlayMP3 {

    /**
     * This is a private method that takes a filepath and plays the mp3 at that location.
     *
     * @param fileLocation the file path of the mp3 file
     */
    private static void loadPlayer(String fileLocation) {
        try {
            FileInputStream fis = new FileInputStream(fileLocation);
            Player playMP3 = new Player(fis);
            playMP3.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the the news-briefing that is saved as file speech.mp3
     */
    public static void playNews() {
        System.out.println("Playing Audio Stream");
        loadPlayer("speech.mp3");

    }

    /**
     * Play the mp3 file that the user specifies in file SmartAlarmConfig.properties
     *
     * @param fileLocation the file-path of the mp3 the user would like to play as a String
     */
    public static void playSong(String fileLocation) {
        System.out.println(String.format("Playing Audio Stream '%s'", fileLocation));
        loadPlayer(fileLocation);
    }
}
