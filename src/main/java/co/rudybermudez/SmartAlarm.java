package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */


import co.rudybermudez.tts.IvonaEngine;

/**
 * Essentially the driver class of the SmartAlarm Program
 */
public class SmartAlarm {

    /**
     * Starts the smartAlarm.
     */
    public void run() {
        try {
            Config config = new Config();
            new IvonaEngine(config).compileNews();
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
