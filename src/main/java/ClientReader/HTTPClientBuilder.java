package ClientReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HTTPClientBuilder
{
    HttpClient httpClient = HttpClient.newBuilder().build();
    private int statusCode;
    private HttpHeaders httpHeaders;

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public JsonObject SendRequest(String method , String uri, Map<String,String> requestBody) throws Exception {

        String jsonObject = new ObjectMapper().writeValueAsString(requestBody);
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(uri)).
                header("Content-Type", "application/json").
                method(method,HttpRequest.BodyPublishers.ofString(jsonObject)).build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        setStatusCode(httpResponse.statusCode());
        setHttpHeaders(httpResponse.headers());
        return new Gson().fromJson(httpResponse.body(), JsonObject.class);
    }

    //Overloading the base function for GET method
    // Can Remove this function and send an empty Hashmap in get request instead of using overloaded function
    public JsonObject SendRequest(String method , String uri) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(uri)).
                header("Content-Type", "application/json").
                method(method,HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        setStatusCode(httpResponse.statusCode());
        setHttpHeaders(httpResponse.headers());
        return new Gson().fromJson(httpResponse.body(), JsonObject.class);
    }
}
