package it.lucadev;

import io.quarkus.test.junit.QuarkusTestProfile;

public class H2TestProfile implements QuarkusTestProfile {
    @Override
    public String getConfigProfile() {
        return "h2";
    }
}
