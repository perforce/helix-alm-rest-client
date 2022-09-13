/*
 * *****************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (c) 2022, Perforce Software, Inc.  
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of 
 * this software and associated documentation files (the "Software"), to deal in 
 * the Software without restriction, including without limitation the rights to use, 
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the 
 * Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 * *****************************************************************************
 */

package com.perforce.halm.rest;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * SSL // HTTPS Certificate utilities
 */
public final class CertUtils {
    /**
     * Constructor, private to prevent initialization
     */
    private CertUtils() {
        // static class
    }

    /**
     * Returns a list of server certificates in PEM format.
     *
     * @param connectionInfo - Helix ALM REST API connection information.
     * @return - Returns a list of server certificates in PEM format.
     * @throws IOException - Throws an exception.
     */
    public static List<String> getServerCertificates(ConnectionInfo connectionInfo) throws IOException {
        HttpClientUtils.CertDownloadingUnsafeTrustManager certDownloadingUnsafeTrustManager = new HttpClientUtils.CertDownloadingUnsafeTrustManager();
        OkHttpClient client = HttpClientUtils.getUnsafeOkHttpClient(certDownloadingUnsafeTrustManager);
        Request request = getCertTestRequest(connectionInfo);

        client.newCall(request).execute();

        return certDownloadingUnsafeTrustManager.getPemCertificates();
    }

    /**
     * Checks the status of the server certificate. Note: This is NOT a reliable method for checking to see if we can
     * connect to the server. For that it is actually better to use {@link #getServerCertificates(ConnectionInfo)} or
     * better {@link Client#doesServerExist()}
     *
     * @param connectionInfo - Server connection information
     * @return Returns the status of the server certificate
     */
    public static CertificateInfo getServerCertStatus(ConnectionInfo connectionInfo) {
        CertificateInfo result = new CertificateInfo();

        // If we were given a certificate, then we need to build a 'trusting' client that trusts that certificate.
        OkHttpClient client;
        if (connectionInfo.pemCerts != null) {
            client = HttpClientUtils.getTrustingOkHttpClient(connectionInfo.pemCerts);
        }
        else {
            client = HttpClientUtils.getSafeOkHttpClient();
        }

        Request request = getCertTestRequest(connectionInfo);

        try(Response response = client.newCall(request).execute()) {
            // If no exception was thrown, it indicates that the SSL Certificate is valid.
            if (connectionInfo.pemCerts != null) {
                result.setStatus(CertificateStatus.TRUSTED);
            }
            else {
                result.setStatus(CertificateStatus.VALID);
            }
        }
        catch (SSLHandshakeException | SSLPeerUnverifiedException err) {
            result.setErrorMessage(err.getMessage());

            // Certificate is possibly self-signed, expired, other. Give back as much information as possible.
            HttpClientUtils.CertDownloadingUnsafeTrustManager downloadingUnsafeTrustManager = new HttpClientUtils.CertDownloadingUnsafeTrustManager();
            OkHttpClient unsafeClient = HttpClientUtils.getUnsafeOkHttpClient(downloadingUnsafeTrustManager);
            try (Response unsafeResponse = unsafeClient.newCall(request).execute()) {
                // If no exception was thrown, the client has successfully connected. Give back information that can be used to accept the certificates.
                result.setStatus(CertificateStatus.INVALID_DOWNLOADABLE);
                result.setFingerprints(downloadingUnsafeTrustManager.getFingerprints());
                result.setPemCertificates(downloadingUnsafeTrustManager.getPemCertificates());
            }
            catch (Exception e) {
                result.setErrorMessage(e.getMessage());
                //todo: ENHANCEMENT: Implement better logging.
                e.printStackTrace();
            }
        }
        catch (Exception ex) {
            // If any other exception was thrown, return the exception message as the error message.
            result.setErrorMessage(ex.getMessage());

            //todo: ENHANCEMENT: Implement better logging.
            ex.printStackTrace();
        }

        return result;
    }

    /**
     * Builds the request used to check the certificates.
     *
     * @param connectionInfo Connection information for the server
     * @return Returns the newly constructed request
     */
    private static Request getCertTestRequest(ConnectionInfo connectionInfo) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(connectionInfo.url)).newBuilder()
            .build();

        return new Request.Builder()
            .url(url)
            .build();
    }
}
