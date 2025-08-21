package org.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class SearchBreweriesBaseTest {
    
    protected static final String BASE_URI = "https://api.openbrewerydb.org";
    protected static final String SEARCH_ENDPOINT = "/v1/breweries/search";
    
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }
    
}