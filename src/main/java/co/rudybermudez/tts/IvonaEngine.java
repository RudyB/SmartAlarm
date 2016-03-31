package co.rudybermudez.tts;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

import co.rudybermudez.Config;
import co.rudybermudez.TextCompiler;
import com.ivona.services.tts.IvonaSpeechCloudClient;
import com.ivona.services.tts.model.CreateSpeechRequest;
import com.ivona.services.tts.model.CreateSpeechResult;
import com.ivona.services.tts.model.Input;
import com.ivona.services.tts.model.Voice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Class that generates text-to-speech synthesis and retrieves audio stream. Converts the text of
 * {@link TextCompiler}.soundAlarm() to audio and saves it with the filename speech.mp3
 */
public class IvonaEngine {


    /**
     * mConfig is an instance of the class {@link Config} that gets the user properties from SmartAlarmConfig.properties.
     */
    private final Config mConfig;


    /**
     * mSpeechCloud is an instance of {@link IvonaSpeechCloudClient} that synthesizes the text into speech.
     */
    private IvonaSpeechCloudClient mSpeechCloud;


    /**
     * Default constructor. Initializes the config, and the {@link IvonaSpeechCloudClient} mSpeechCloud.
     *
     * @param config An instance of {@link Config}
     */
    public IvonaEngine(Config config) {
        mConfig = config;
        mSpeechCloud = new IvonaSpeechCloudClient(new IvonaCredentials(mConfig.getIvonaSecretKey(), mConfig.getIvonaAccessKey()));
        mSpeechCloud.setEndpoint("https://tts.eu-west-1.ivonacloud.com");
    }

    /**
     * compileNews() converts the text of {@link TextCompiler}.soundAlarm() to audio and saves it with the filename speech.mp3
     *
     * @throws Exception exception is caused by {@link TextCompiler}.soundAlarm()
     */
    public void compileNews() throws Exception {
        String outputFileName = "speech.mp3";
        CreateSpeechRequest createSpeechRequest = new CreateSpeechRequest();
        Input input = new Input();
        Voice voice = new Voice();

        voice.setName("Salli");
        String text = new TextCompiler(mConfig).soundAlarm();
        System.out.println(text);
        input.setData(text);

        createSpeechRequest.setInput(input);
        createSpeechRequest.setVoice(voice);
        InputStream in = null;
        FileOutputStream outputStream = null;

        try {

            CreateSpeechResult createSpeechResult = mSpeechCloud.createSpeech(createSpeechRequest);
            System.out.println("\n\n\n\nStarting to retrieve audio stream");
            in = createSpeechResult.getBody();
            outputStream = new FileOutputStream(new File(outputFileName));
            byte[] buffer = new byte[2 * 1024];
            int readBytes;
            while ((readBytes = in.read(buffer)) > 0) {
                // In the example we are only printing the bytes counter,
                // In real-life scenario we would operate on the buffer
                // System.out.println(" received bytes: " + readBytes);
                outputStream.write(buffer, 0, readBytes);
            }

            System.out.println("Audio Stream Received");
        } finally {
            if (in != null) {
                in.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}