# OpenBreweryDB API Test Suite

## Search Breweries
## HTTP method under TEST (First part)
- **Base URL**: `https://api.openbrewerydb.org`
- **Endpoint**: `GET /v1/breweries/search`
- **Documentation**: https://www.openbrewerydb.org/documentation

## Test coverage (up to 5 scenarios) that covering main features
1. **Happy Path** - Successful search with valid query and per_page parameter
2. **Pagination** - Verify per_page parameter (max 15 breweries limit)
3. **Empty Results** - Search with non-existent brewery names
4. **Contract** - JSON schema validation for response structure
5. **Query Format** - Underscore format in query parameter


## Libraries and tools used
- **Language**: Java
- **Testing Framework**: JUnit 5
- **API Testing**: REST Assured 5.5.6
- **Reporting**: Allure 2.29.1
- **Build Tool**: Gradle

## 2. List Breweries (Second part)
**Endpoint** 'GET /v1/breweries'

**Positive Tests**

1. Happy Path - Default request returns breweries list (OK 200)
2. Pagination - per_page parameter limits results (take into consideration  Default per page 50. Max per page is 200.)
3. Filtering - by types, test all types works
4. Sorting - sort parameter orders results correctly (ascending, descending), note by_dist does not work with sort
5. Multiple Parameters - Combined filters work together
6. Edge Values - per_page=1 and per_page=200 (boundary values)

**Negative Tests**

7. Invalid per_page - Values > 200
8. Invalid Filters - Non-existent values for filtering, check backend handles the request
9. Invalid Sorting
10. Empty Results - Valid query returning no matches
11. Special Characters - URL encoding in filter values

**Contract Testis - JSON Schema validation**

12. Response Structure - JSON schema validation for brewery objects (Required Fields, Data Types, Array Structure)

**Performance Tests** (Should be tested separatelty without REST assured, Jmeter or other tools)

13. Response Time - Requests complete within SLA

**Security Tests**

14. SQL Injection - Malicious input in filter parameters
15. XSS Vulnerability - Script injection in query parameters
16. Input Validation - Boundary testing for all parameters

**Edge Cases**

17. Empty Parameters - Empty string values in filters
18. Unicode Characters - International characters in city/state names
19. Case Sensitivity - Upper/lower case in filter values (one test is sufficient, low priority)
20. URL Encoding - Spaces and special chars in parameters (if test library allows)

**Error Handling**

21. HTTP Status Codes - Proper 400/404/500 responses
22. Error Messages - Clear error descriptions (optional)
23. Incorrect Requests - Invalid JSON/headers handling (if library allows to send it, might be validation test library validation)

**TEST ESTIMATION:**
- Optimistic 6 hrs
- Realistic 8 hrs
- Pessimistic 10 hrs

## How to run

```bash
# Run all tests
./gradlew test

# Run only search tests
./gradlew runSearchTests

# Generate Allure report
./gradlew test allureReport
```

## Project Structure
```
src/test/java/
├── org/api/
│   └── SearchBreweriesBaseTest.java
└── resources/
    └── schemas/
        └── AutocompleteSchema.json
```

## Constraints
- **Maximum results**: 15 breweries per response (regardless of per_page)
- **Query format**: Supports spaces (URL encoded) or underscores
- **Response format**: JSON array of brewery objects