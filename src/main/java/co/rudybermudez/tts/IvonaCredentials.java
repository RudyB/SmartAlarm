package co.rudybermudez.tts;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

/**
 * A custom implementation of {@link AWSCredentialsProvider} used in {@link IvonaEngine}.
 */
public class IvonaCredentials implements AWSCredentialsProvider {

    /**
     * The ivona secret key.
     */
    private final String mSecretKey;
    /**
     * The ivona access key.
     */
    private final String mAccessKey;

    /**
     * Instantiates a new Ivona credentials.
     *
     * @param mSecretKey the secret key of the user
     * @param mAccessKey the access key of the user
     */
    public IvonaCredentials(String mSecretKey, String mAccessKey) {
        super();
        this.mSecretKey = mSecretKey;
        this.mAccessKey = mAccessKey;
    }

    /**
     * Gets the user's credentials as type {@link AWSCredentials}.
     *
     * @return An instance of {@link AWSCredentials} with the user's credentials
     */
    @Override
    public AWSCredentials getCredentials() {
        return new AWSCredentials() {

            @Override
            public String getAWSSecretKey() {

                return mSecretKey;
            }

            @Override
            public String getAWSAccessKeyId() {

                return mAccessKey;
            }

        };
    }

    /**
     * Non-implemented function
     */
    @Override
    public void refresh() {


    }


}
