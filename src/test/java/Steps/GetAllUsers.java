package Steps;


import ClientReader.BaseReader;
import ClientReader.HTTPClientBuilder;
import POJO.HttpResponseMessages;
import POJO.HttpResponseStatusCodes;
import com.google.gson.JsonObject;
import io.cucumber.java.en.Given;
import org.junit.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class GetAllUsers {

    BaseReader baseReader = new BaseReader();
    String  baseURI = baseReader.getUrl();
    HTTPClientBuilder httpClientBuilder = new HTTPClientBuilder();
    HttpResponseStatusCodes statusCodes = new HttpResponseStatusCodes();
    HttpResponseMessages responseMessages = new HttpResponseMessages();
    Map<String,String> requestBody =  new HashMap<>();

    @Given("User Gets All Signed up Users")
    public void userGetsAllSignedUpUsers() throws IOException, InterruptedException {
        JsonObject responseBody = httpClientBuilder.SendRequest("GET",baseURI+"/users?page=2");
        Assert.assertEquals(2,responseBody.get("page").getAsInt());
        Assert.assertEquals("michael.lawson@reqres.in",
                responseBody.getAsJsonArray("data").get(0).getAsJsonObject().asMap().get("email").getAsString());
        Assert.assertEquals(statusCodes.ok,httpClientBuilder.getStatusCode());
    }

    @Given("Get a Single User")
    public void GetASingleUser() throws IOException, InterruptedException {
        JsonObject responseBody = httpClientBuilder.SendRequest("GET",baseURI+"/users/2");
        Assert.assertEquals("janet.weaver@reqres.in",
                responseBody.getAsJsonObject("data").get("email").getAsString());
        Assert.assertEquals("https://reqres.in/#support-heading",
                responseBody.getAsJsonObject("support").get("url").getAsString());
        Assert.assertEquals(statusCodes.ok,httpClientBuilder.getStatusCode());
    }

    @Given("Create New User")
    public void CreateNewUser() throws Exception {
        requestBody.put("name","Mohammed Fahmy");
        requestBody.put("job","SQE");
        JsonObject responseBody = httpClientBuilder.SendRequest("POST",baseURI+"/users",requestBody);
        Assert.assertEquals(requestBody.get("name"),responseBody.get("name").getAsString());
        Assert.assertEquals(requestBody.get("job"),responseBody.get("job").getAsString());
        Assert.assertEquals(statusCodes.created,httpClientBuilder.getStatusCode());
    }

    @Given("Delete Existing User")
    public void deleteExistingUser() throws IOException, InterruptedException {
        JsonObject responseBody = httpClientBuilder.SendRequest("DELETE",baseURI+"/users/2");
        Assert.assertEquals(statusCodes.noContent,httpClientBuilder.getStatusCode());
        Assert.assertNull(responseBody);
    }

    @Given("Update User")
    public void updateUser() throws Exception {
        requestBody.put("name","Mohammed Fahmy");
        requestBody.put("job","SQE");
        JsonObject responseBody = httpClientBuilder.SendRequest("PUT",baseURI+"/users/2",requestBody);
        Assert.assertEquals(statusCodes.ok,httpClientBuilder.getStatusCode());
        Assert.assertEquals(requestBody.get("name"),responseBody.get("name").getAsString());
        Assert.assertEquals(requestBody.get("job"),responseBody.get("job").getAsString());
        Assert.assertNotNull(responseBody.get("updatedAt"));
    }

    @Given("Get A Not Found User")
    public void getANotFoundUser() throws IOException, InterruptedException {
        JsonObject responseBody = httpClientBuilder.SendRequest("GET",baseURI+"/users/23");
        Assert.assertEquals(JsonObject.class,responseBody.getClass());
        Assert.assertEquals(statusCodes.notFound,httpClientBuilder.getStatusCode());
    }

    @Given("Unsuccessful SignIn")
    public void UnsuccessfulSignIn() throws Exception {
        requestBody.put("email","eve.holt@reqres.in");
        JsonObject responseBody = httpClientBuilder.SendRequest("POST",baseURI+"/register",requestBody);
        Assert.assertEquals(statusCodes.badRequest,httpClientBuilder.getStatusCode());
        Assert.assertEquals(responseMessages.unsuccessfulSignIn,responseBody.get("error").getAsString());
    }

    @Given("Successful SignIn")
    public void successfulSignIn() throws Exception {
        requestBody.put("email","eve.holt@reqres.in");
        requestBody.put("password","pistol");
        JsonObject responseBody = httpClientBuilder.SendRequest("POST",baseURI+"/register",requestBody);
        Assert.assertEquals(statusCodes.ok,httpClientBuilder.getStatusCode());
        Assert.assertEquals(4,responseBody.get("id").getAsInt());
        Assert.assertEquals(String.class,responseBody.get("token").getAsString().getClass());
    }
}