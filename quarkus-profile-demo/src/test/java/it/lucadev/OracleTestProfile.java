package it.lucadev;

import io.quarkus.test.junit.QuarkusTestProfile;

public class OracleTestProfile implements QuarkusTestProfile {
    @Override
    public String getConfigProfile() {
        return "oracle";
    }
}
