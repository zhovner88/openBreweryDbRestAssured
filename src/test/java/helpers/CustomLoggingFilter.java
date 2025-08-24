package helpers;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class CustomLoggingFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        
        System.out.println("\n=== REQUEST ===");
        System.out.println("Method: " + requestSpec.getMethod());
        System.out.println("URI: " + requestSpec.getURI());
        
        Response response = ctx.next(requestSpec, responseSpec);
        
        System.out.println("\n=== RESPONSE ===");
        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Content-Type: " + response.getContentType());
        System.out.println("Body: " + response.getBody().asPrettyString());
        
        return response;
    }
}