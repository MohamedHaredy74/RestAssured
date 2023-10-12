package api.tests;

import api.endpoints.UserEndPoints;
import api.utils.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserTests {


    @Description("Test1: create multiple users using json data file ")
    @Severity(SeverityLevel.CRITICAL)
    @Step("1. Send the post request 2. Verify status code 200 3. verify content-type header 4.verify response Time ")
    @Test(priority = 1,dataProvider = "user data payload",dataProviderClass = UserData.class)
    public void createUserStatusCode200(Object userPayload)
    {
        Response response=
        UserEndPoints.createUser(userPayload);
        response.then().log().ifError();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.header("content-type"),"application/json");
        Assert.assertTrue(response.time() < 3000);
    }


    @Description("Test2: get created users using username ")
    @Severity(SeverityLevel.CRITICAL)
    @Step("1. Send the get user request 2. Verify status code 200  3. verify body-username attribute 4. verify access-control-allow-headers ")
    @Test(priority = 2,dataProvider = "usernames payload",dataProviderClass = UserData.class)
    public void getUserByName(Object username)
    {
        String userName= username.toString();
        Response response=
                UserEndPoints.getUser(username);
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.body().jsonPath().get("username"),userName);
        Assert.assertEquals(response.getHeader("access-control-allow-headers"),
                "Content-Type, api_key, Authorization");

    }

    @Description("Test3: update the created users using username ")
    @Severity(SeverityLevel.CRITICAL)
    @Step("1. send the update request 2. print the response body 3. verify status code 200 4." +
            " verify body-type attribute 5. verify content-type header" )
    @Test(priority = 3,dataProvider = "user data payload",dataProviderClass = UserData.class)
    public void updateUserByName(Object payload)
    {
        JSONObject jsonObject= (JSONObject) payload;
        String username=jsonObject.get("username").toString();
        Response response=
                UserEndPoints.updateUser(payload,username);
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.body().jsonPath().get("type"),"unknown");
        Assert.assertEquals(response.getHeader("content-type"),"application/json");

    }


    @Description("Test4: delete the created users using username ")
    @Severity(SeverityLevel.CRITICAL)
    @Step("1. sent delete user request 2. log response If error 3. verify status code 200 4. sent get user request to verify deleting request ")
    @Test(priority = 4,dataProvider = "usernames payload",dataProviderClass = UserData.class)
    public void deleteUserByName(Object username)
    {
        Response response=
                UserEndPoints.deleteUser(username);
        response.then().log().ifError();
        Assert.assertEquals(response.statusCode(),200);
        UserEndPoints.getUser(username).then().statusCode(404);

    }



}
