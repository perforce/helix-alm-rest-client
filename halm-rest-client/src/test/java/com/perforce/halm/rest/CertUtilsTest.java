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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CertUtilsTest {
    @ParameterizedTest
    @ValueSource( strings = {
        "https://example.com/",
        "https://self-signed.badssl.com/",
        "https://untrusted-root.badssl.com/"
    })
    void getServerCertificates(String url) {
        var connInfo = new ConnectionInfo(url, "", "");
        try {
            List<String> certs = CertUtils.getServerCertificates(connInfo);
            assertTrue( certs.size() > 0, "Certificate array was empty?");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void getServerCertificates_BadURL() {
        var connInfo = new ConnectionInfo("https://squigglejones", "", "");
        try {
            List<String> certs = CertUtils.getServerCertificates(connInfo);
            fail( "Should have thrown an exception.");
        } catch (Exception e) {
            assertEquals(UnknownHostException.class, e.getClass());
        }
    }

    @Test
    void getServerCertificates_HTTP() {
        var connInfo = new ConnectionInfo("http://www.example.com", "", "");
        try {
            List<String> certs = CertUtils.getServerCertificates(connInfo);
            assertEquals(0, certs.size(), "Certificate array was not empty?");
        } catch (Exception e) {
            fail(e);
        }
    }

    private static Stream<Arguments> provideInputsForGetServerCertStatus() {
        return Stream.of(
            Arguments.of("http://example.com/", CertificateStatus.VALID),
            Arguments.of("https://example.com/", CertificateStatus.VALID),
            Arguments.of("https://self-signed.badssl.com/", CertificateStatus.INVALID_DOWNLOADABLE),
            Arguments.of("https://expired.badssl.com/", CertificateStatus.INVALID_DOWNLOADABLE),
            Arguments.of("https://revoked.badssl.com/", CertificateStatus.INVALID_DOWNLOADABLE),
            Arguments.of("https://wrong.host.badssl.com/", CertificateStatus.INVALID_DOWNLOADABLE),
            Arguments.of("https://untrusted-root.badssl.com/", CertificateStatus.INVALID_DOWNLOADABLE),
            Arguments.of("https://pinning-test.badssl.com/", CertificateStatus.VALID)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInputsForGetServerCertStatus")
    void getServerCertStatus(String url, CertificateStatus expectedStatus) {
        var connInfo = new ConnectionInfo(url, "", "");
        var certInfo = CertUtils.getServerCertStatus(connInfo);
        assertEquals(expectedStatus, certInfo.getStatus());
    }

    @ParameterizedTest
    @ValueSource( strings = {
        "https://example.com/",
        "https://self-signed.badssl.com/",
        "https://untrusted-root.badssl.com/",
        "https://wrong.host.badssl.com"
    })
    void retrieveAndUseCerts(String url) {
        // Retrieve the certs
        var connInfo = new ConnectionInfo(url, "", "");
        try {
            connInfo.pemCerts = CertUtils.getServerCertificates(connInfo);
            assertTrue( connInfo.pemCerts.iterator().hasNext(), "Certificate array was empty?");
        } catch (Exception e) {
            fail(e);
        }

        // Now, try to use the certs
        var certInfo = CertUtils.getServerCertStatus(connInfo);
        assertEquals(CertificateStatus.TRUSTED, certInfo.getStatus());
    }

    @Test
    void sslCertForHostnameMismatch() {
        var connInfo = new ConnectionInfo("https://wrong.host.badssl.com", "", "");
        try {
            connInfo.pemCerts = CertUtils.getServerCertificates(connInfo);
            assertTrue( connInfo.pemCerts.iterator().hasNext(), "Certificate array was empty?");
        } catch (Exception e) {
            fail(e);
        }

        // Now, try to use the certs
        var certInfo = CertUtils.getServerCertStatus(connInfo);
        assertEquals(CertificateStatus.TRUSTED, certInfo.getStatus());
    }

    @Test
    void sslCertsDontMatchHostMismatch() {
        var connInfo = new ConnectionInfo("https://self-signed.badssl.com/", "", "");
        try {
            connInfo.pemCerts = CertUtils.getServerCertificates(connInfo);
            connInfo.url = "https://wrong.host.badssl.com";
            assertTrue( connInfo.pemCerts.iterator().hasNext(), "Certificate array was empty?");
        } catch (Exception e) {
            fail(e);
        }

        // Now, try to use the certs
        var certInfo = CertUtils.getServerCertStatus(connInfo);
        assertEquals(CertificateStatus.INVALID_DOWNLOADABLE, certInfo.getStatus());
    }
}
