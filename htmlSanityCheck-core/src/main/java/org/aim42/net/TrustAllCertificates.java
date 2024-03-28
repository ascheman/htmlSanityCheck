package org.aim42.net;

// created by https://www.geekality.net/2013/09/27/java-ignore-ssl-certificate-errors/

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public final class TrustAllCertificates implements X509TrustManager, HostnameVerifier
{
    public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
    public void checkClientTrusted(X509Certificate[] certs, String authType) { //NOSONAR(S4830)
        /* As we do only read external content, we do not care much about SSL certs */
    }
    public void checkServerTrusted(X509Certificate[] certs, String authType) { //NOSONAR(S4830)
        /* As we do only read external content, we do not care much about SSL certs */
    }
    public boolean verify(String hostname, SSLSession session) {
        /* As we do only read external content, we do not care much about hostname equality */
        return !hostname.isEmpty();
    }

    /**
     * Installs a new {@link TrustAllCertificates} as trust manager and hostname verifier.
     */
    public static void install()
    {
        try
        {
            TrustAllCertificates trustAll = new TrustAllCertificates();

            // Install the all-trusting trust manager
            // Intentiaonally using "SSL" to make it work with a low security level
            SSLContext sc = SSLContext.getInstance("SSL"); //NOSONAR(S4424)
            sc.init(null,
                    new TrustManager[]{trustAll},
                    new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(trustAll);
        }
        catch (NoSuchAlgorithmException | KeyManagementException e)
        {
            throw new RuntimeException("Failed setting up all thrusting certificate manager.", e);
        }

    }
}

/*=============================================================
  Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an
 "AS IS" BASIS,WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 =============================================================*/
