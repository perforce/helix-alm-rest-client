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

import okhttp3.OkHttpClient;
import okhttp3.tls.Certificates;
import okhttp3.tls.HandshakeCertificates;
import org.apache.commons.codec.digest.DigestUtils;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utilities for creating specific purpose HttpClients
 */
final class HttpClientUtils {
    /**
     * Constructor, private due to static class
     */
    private HttpClientUtils() {}

    /**
     * Builds a 'trusting' OkHTTPClient that 'trusts' the list of specified certificates.
     *
     * @param pemCertContents - Certificates to trust. Should be in the PEM format.
     * @return - Returns a newly constructed OkHTTPClient
     */
    public static OkHttpClient getTrustingOkHttpClient(Iterable<String> pemCertContents) {
        // Set up a trust manager that knows about the certificates we want to trust.
        HandshakeCertificates.Builder handshakeBuilder = new HandshakeCertificates.Builder();
        ArrayList<X509Certificate> certs = new ArrayList<>();
        for (final String pemCert : pemCertContents) {
            X509Certificate cert = Certificates.decodeCertificatePem(pemCert);
            // TODO: ENHANCEMENT: Implement better logging to indicate potentially invalid certificate is being accepted.

            handshakeBuilder.addTrustedCertificate(cert);
            certs.add(cert);
        }

        HandshakeCertificates certificates = handshakeBuilder.addPlatformTrustedCertificates().build();

        // Start building the HTTP client
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .sslSocketFactory(certificates.sslSocketFactory(), certificates.trustManager());

        // Try to set up a hostname verifier that specifically trusts the certificates, but doesn't care if the
        // CN embedded in the certificate doesn't actually match the host.
        try {
            builder.hostnameVerifier(new TrustingHostnameVerifier(certs));
        } catch (CertificateEncodingException e) {
            // TODO: ENHANCEMENT: Implement better logging of exceptions.
            e.printStackTrace();
        }

        return builder.build();
    }

    /**
     * Construct a standard, safe, OkHttpClient
     *
     * @return - A standard, safe, OkHttpClient
     */
    public static OkHttpClient getSafeOkHttpClient() {
        return new OkHttpClient();
    }

    /**
     * Construct a wholly UNSAFE OkHttpClient. This will trust every single invalid or bad HTTPS certificate.
     * This should NOT be used as part of talking to the REST API. Either a 'Safe' or 'Trusting' OkHttpClient should
     * be used.
     *
     * This should only be used for limited purposes. For example, downloading a certificate fom a site signed
     * with a self-signed certificate. This requires we connect to the server, and that requires an unsafe client.
     *
     * @return Returns an OkHttpClient that trusts everyone.
     */
    static OkHttpClient getUnsafeOkHttpClient() {
        return getUnsafeOkHttpClient(new UnsafeTrustManager());
    }

    /**
     * Construct a wholly UNSAFE OkHttpClient. This will trust every single invalid or bad HTTPS certificate.
     * This should NOT be used as part of talking to the REST API. Either a 'Safe' or 'Trusting' OkHttpClient should
     * be used.
     *
     * This should only be used for limited purposes. For example, downloading a certificate fom a site signed
     * with a self-signed certificate. This requires we connect to the server, and that requires an unsafe client.
     *
     * @param unsafeTrustManager Unsafe trust manager to use. This should be a trust manager that allows pretty much
     *                           everyone through.
     * @return Returns an OkHttpClient that trusts everyone.
     */
    static OkHttpClient getUnsafeOkHttpClient(X509TrustManager unsafeTrustManager) {
        final TrustManager[] trustAllCerts = new TrustManager[] { unsafeTrustManager };

        try {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL"); //NOSONAR
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // Construct an HTTP Client that accepts everything.
            return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                .hostnameVerifier((hostname, session) -> true).build(); //NOSONAR
        }
        catch (NoSuchAlgorithmException | KeyManagementException e) {
            // TODO: ENHANCEMENT: Implement better logging of exceptions.
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * This allows us to accept certificates that use IP addresses as the CN, and it allows us to accept situations where
     * the certificates CN just plain doesn't match the actual host name. This is only used if the client specifically
     * says they want to accept invalid certificates and provides the certificates they want to accept.
     */
    static class TrustingHostnameVerifier implements HostnameVerifier {
        private final Map<String, X509Certificate> trustedCerts = new HashMap<>();

        TrustingHostnameVerifier(List<X509Certificate> inTrustedCerts) throws CertificateEncodingException {
            for (X509Certificate cert : inTrustedCerts) {
                trustedCerts.put(HttpClientUtils.getCertificateFingerprint(cert), cert);
            }
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            boolean result;

            try {
                // Verify the requested hostname matches the session's hostname. Also verify that we have
                // the same number of 'trusted' certificates vs. returned Peer certificates.
                result = hostname.trim().equalsIgnoreCase(session.getPeerHost().trim()) &&
                    trustedCerts.size() == session.getPeerCertificates().length;

                // Verify that each of the returned peer certificates matches one of the trusted certificates.
                if (result) {
                    for (Certificate cert : session.getPeerCertificates()) {
                        Certificate trustedCert = trustedCerts.get(HttpClientUtils.getCertificateFingerprint(cert));

                        result = trustedCert.equals(cert);
                        if (!result) {
                            break;
                        }
                    }
                }
            } catch (SSLPeerUnverifiedException | CertificateEncodingException e) {
                result = false;
            }

            return result;
        }
    }

    /**
     * Encodes the provided certificate as a sha1 hash.
     *
     * @param certificate Certificate to encode
     * @return Returns the encoded certificate as a sha1 hex hash.
     * @throws CertificateEncodingException If an encoding error occurs
     */
    private static String getCertificateFingerprint(Certificate certificate) throws CertificateEncodingException {
        return DigestUtils.sha1Hex(certificate.getEncoded());
    }

    /**
     * This is a special version of the X509TrustManager that will download the certificates for a website
     */
    static class CertDownloadingUnsafeTrustManager implements X509TrustManager {

        /**
         * List of certificates that were downloaded
         */
        private final List<X509Certificate> certificates = new ArrayList<>();

        /**
         * @return - Returns the list of downloaded certificates
         */
        public List<X509Certificate> getCertificates() {
            return new ArrayList<>(certificates);
        }

        public List<String> getPemCertificates() {
            return this.certificates.stream().map(Certificates::certificatePem).collect(Collectors.toList());
        }

        public List<String> getFingerprints() throws CertificateEncodingException {
            List<String> list = new ArrayList<>();
            for (X509Certificate x : this.certificates) {
                list.add(HttpClientUtils.getCertificateFingerprint(x));
            }
            return list;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // Trust everything.
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // Trust everything, and download the certificate
            certificates.addAll(Arrays.asList(chain));
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * This is a special version of the X509TrustManager that can be used as part of determining what the state
     * of the certificate is on a specific server, and if is one we could download.
     */
    static class UnsafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // Trust everything.
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // Trust everything.
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
