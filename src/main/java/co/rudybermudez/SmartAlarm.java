package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

public class SmartAlarm {

    public void run() {
        try {
            Config config = new Config();
            new TextToSpeech(config).compileNews();
            PlayMP3.playNews();
            if (config.getEnableMusic()) {
                PlayMP3.playSong(config.getSongLocation());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
