# OpenBreweryDB API Test Suite

## HTTP method under TEST 
- **Base URL**: `https://api.openbrewerydb.org`
- **Endpoint**: `GET /v1/breweries/search`
- **Documentation**: https://www.openbrewerydb.org/documentation

## Libraries and tools used 
- **Language**: Java
- **Testing Framework**: JUnit 5
- **API Testing**: REST Assured 5.5.6
- **Reporting**: Allure 2.29.1
- **Build Tool**: Gradle

## Test coverage (up to 5 scenarios)

## How to run

```bash
# Run all tests
./gradlew test

# Run only search tests
./gradlew runSearchTests

# Generate Allure report
./gradlew test allureReport
```

## Additional Test Scenarios for Complete Suite

The following scenarios should be included for comprehensive API test coverage:

## Project Structure
```
src/test/java/
├── org/api/
│   └── SearchBreweriesBaseTest.java
└── resources/
    └── schemas/
        └── brewery-search-schema.json
```

## Constraints
- **Maximum results**: 15 breweries per response (regardless of per_page)
- **Query format**: Supports spaces (URL encoded) or underscores
- **Response format**: JSON array of brewery objects