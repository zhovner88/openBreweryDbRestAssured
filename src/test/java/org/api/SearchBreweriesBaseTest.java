package org.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import helpers.CustomLoggingFilter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SearchBreweriesBaseTest {
    
    protected static final String BASE_URI = "https://api.openbrewerydb.org";
    protected static final String SEARCH_ENDPOINT = "/v1/breweries/search";
    protected static final String AUTOCOMPLETE_ENDPOINT = "/v1/breweries/autocomplete";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.filters(new CustomLoggingFilter());
    }

    @Test
    @Description("Successful autocomplete search, valid query parameters")
    public void testPositiveAutocomplete() {
        given()
            .queryParam("query", "brewery")
        .when()
            .get(AUTOCOMPLETE_ENDPOINT)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", greaterThan(0)); // no need to check for specific values since contract test is here
    }

    @Test
    @Description("Verify that per_page parameter 15 breweries limit for SEARCH_ENDPOINT")
    public void testPaginationLimitPerPageParameter() {
        given()
            .queryParam("query", "brewery")
            .queryParam("per_page", 15)
        .when()
            .get(SEARCH_ENDPOINT)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", lessThanOrEqualTo(15)); // maximum 15 results, but can be less
    }

    @Test
    @Description("Non-existing brewery name return an empty array(negative test)")
    public void testEmptyResulsForNonExistingName() {
        given()
            .queryParam("query", "breweryThatDoesNotExist")
        .when()
            .get(AUTOCOMPLETE_ENDPOINT)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", equalTo(0));
    }

    @Test
    @Description("Json schema validation - contract test")
    public void testValidatesSchemaForAutocomplete() {
        given()
            .queryParam("query", "brewery")
        .when()
            .get(AUTOCOMPLETE_ENDPOINT)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/AutocompleteSchema.json"));
    }

    @Test
    @Description("Underscore format in query parameter handling")
    public void testQueryFormatWithUnderscore() {
        given()
            .queryParam("query", "stone_brewing")
        .when()
            .get(AUTOCOMPLETE_ENDPOINT)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", greaterThanOrEqualTo(0));
    }

}
