package co.rudybermudez;

/*
 * @Author:  Rudy Bermudez
 * @Email:   hello@rudybermudez.co
 * @Date:    Mar 10, 2016
 * @Project: SmartAlarm
 * @Package: co.rudybermudez
 */

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

public class IvonaCredentials implements AWSCredentialsProvider {

    private final String mSecretKey;
    private final String mAccessKey;

    public IvonaCredentials(String mSecretKey, String mAccessKey) {
        super();
        this.mSecretKey = mSecretKey;
        this.mAccessKey = mAccessKey;
    }

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

    @Override
    public void refresh() {


    }


}
