package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

import com.ivona.services.tts.IvonaSpeechCloudClient;
import com.ivona.services.tts.model.CreateSpeechRequest;
import com.ivona.services.tts.model.CreateSpeechResult;
import com.ivona.services.tts.model.Input;
import com.ivona.services.tts.model.Voice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Class that generates sample synthesis and retrieves audio stream.
 */

public class TextToSpeech {

    private final Config mConfig;
    private IvonaSpeechCloudClient speechCloud;

    public TextToSpeech(Config config) {
        mConfig = config;
    }

    private void init() {
        speechCloud = new IvonaSpeechCloudClient(new IvonaCredentials(mConfig.getIvonaSecretKey(), mConfig.getIvonaAccessKey()));
        speechCloud.setEndpoint("https://tts.eu-west-1.ivonacloud.com");
    }

    /**
     * compileNews() converts the text of TextCompiler.soundAlarm() to audio as speech.mp3
     *
     * @throws Exception
     */
    public void compileNews() throws Exception {

        init();

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

            CreateSpeechResult createSpeechResult = speechCloud.createSpeech(createSpeechRequest);

//            System.out.println("\nSuccess sending request:");
//            System.out.println(" content type:\t" + createSpeechResult.getContentType());
//            System.out.println(" request id:\t" + createSpeechResult.getTtsRequestId());
//            System.out.println(" request chars:\t" + createSpeechResult.getTtsRequestCharacters());
//            System.out.println(" request units:\t" + createSpeechResult.getTtsRequestUnits());

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

            //System.out.println("\nFile saved: " + outputFileName);
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