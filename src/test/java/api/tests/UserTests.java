package api.tests;

import api.endpoints.UserEndPoints;
import api.utils.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserTests {


    @Description("Test1: create multiple users using json data file ")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Register")
    @Test(priority = 1,dataProvider = "user data payload",dataProviderClass = UserData.class,description = "Create a User ")
    public void createUserStatusCode200(Object userPayload)
    {
       // step("Send the post request");
        Response response=
        UserEndPoints.createUser(userPayload);
        response.then().log().ifError();
        //step("Verify status code 200");
        Assert.assertEquals(response.getStatusCode(),200);
        //step("verify content-type header");
        Assert.assertEquals(response.header("content-type"),"application/json");
       // step("verify response Time");
        Assert.assertTrue(response.time() < 3000);
    }


    @Description("Test2 : login using created account ")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Login")
    @Test(priority = 2, dataProvider = "usernames-passwords",dataProviderClass = UserData.class ,description = "login with Username and Password")
    public void login(String username,String password)
    {
        Response response=UserEndPoints.login(username,password);
        Assert.assertEquals(response.statusCode(),200);
        response.prettyPrint();
    }


    @Description("Test3: get a created users using username ")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Login")
    @Test(priority = 3,dataProvider = "usernames payload",dataProviderClass = UserData.class ,description = "Get a User")
    public void getUserByName(String username)
    {
        //step("Send the get user request");
        String userName= username.toString();
        Response response=
                UserEndPoints.getUser(username);
        //step("print the response ");
        response.prettyPrint();
        //step("Verify status code 200 ");
        Assert.assertEquals(response.getStatusCode(),200);
       // step("verify body-username attribute");
        Assert.assertEquals(response.body().jsonPath().get("username"),userName);
        //step("verify access-control-allow-headers");
        Assert.assertEquals(response.getHeader("access-control-allow-headers"),
                "Content-Type, api_key, Authorization");
    }

    @Description("Test4: update a specific user using username ")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Rest user Data")
    @Test(priority = 4,dataProvider = "user data payload",dataProviderClass = UserData.class ,description = "Update the User data")
    public void updateUserByName(Object payload)
    {
       // step("send the update request");
        JSONObject jsonObject= (JSONObject) payload;
        String username=jsonObject.get("username").toString();
        Response response=
                UserEndPoints.updateUser(payload,username);
        //step("verify status code 200");
        Assert.assertEquals(response.statusCode(),200);
      //  step("verify body-type attribute");
        Assert.assertEquals(response.body().jsonPath().get("type"),"unknown");
      //  step("verify content-type header");
        Assert.assertEquals(response.getHeader("content-type"),"application/json");

    }

    @Description("Test5: delete a specific user using username ")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Delete Account")
    @Test(priority = 5,dataProvider = "usernames payload",dataProviderClass = UserData.class , description = "Delete a Specific User ")
    public void deleteUserByName(String username)
    {
        //step("sent delete user request");
        Response response=
                UserEndPoints.deleteUser(username);
        response.then().log().ifError();
        //step("verify status code 200");
        Assert.assertEquals(response.statusCode(),200);
        //step(" sent get user request to verify deleting request");
        UserEndPoints.getUser(username).then().statusCode(404);
    }


}
