package com.perforce.halm.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "https://192.0.2.123:8443", // IP Address that acts funny on corporate network
        "http://192.0.2.123:8443",  // IP Address that acts funny on corporate network
        "https://badURL.example.com",  // Non existent subdomain
        "http://stillBad.example.com", // Non existent subdomain
        "https://hostIsJustMadeUpByMeNow.com",   // Non existent address
        "https://ITotallyJustMadeThisUpasdfasdf",// Non existent address
        "https://www.example.com",     // Valid domain, but not REST API
        "https://expired.badssl.com/", // Very Bad SSL, also, not REST API
        "https://self-signed.badssl.com/" // Self Signed, not REST API

    })
    void testBadHosts(String url) {
        Client client = new Client(new ConnectionInfo(url, "", ""));
        Exception ex = client.doesServerExist();
        assertNotNull(ex);
    }

    @Test
    void testGoodHost() {
        Client client = new Client(new ConnectionInfo("https://almprod01.das.perforce.com:8443", "", ""));
        Exception ex = client.doesServerExist();
        assertNull(ex);
    }
}
