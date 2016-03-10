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

public class PlayMP3 {

    private static void loadPlayer(String filelocation) {
        try {
            FileInputStream fis = new FileInputStream(filelocation);
            Player playMP3 = new Player(fis);
            playMP3.play();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void playNews() {
        System.out.println("Playing Audio Stream");
        loadPlayer("speech.mp3");

    }

    public static void playSong(String fileLocation) {
        System.out.println(String.format("Playing Audio Stream '%s'", fileLocation));
        loadPlayer(fileLocation);
    }
}
