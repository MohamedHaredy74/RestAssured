package api.endpoints;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserEndPoints {


    public static Response  createUser( Object payload)
    {
        Response response=
        given().filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
        .when()
                .post(Routes.post_user_url);
        return response;
    }

    public static Response getUser(Object username)
    {
        Response response=
                given().filter(new AllureRestAssured())
                        .accept(ContentType.JSON)
                        .pathParam("username", username.toString())
                .when()
                        .get(Routes.get_user_url);

        return response;
    }

    public static Response login(String username ,String password)
    {
        Response response=
                given().filter(new AllureRestAssured())
                        .accept(ContentType.JSON)
                        .queryParam("username",username)
                        .queryParam("password",password)
                        .when()
                        .get(Routes.login_user_url);

        return response;
    }


    public static Response updateUser(Object payload ,String username)
    {
        Response response=
                given().filter(new AllureRestAssured())
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(payload)
                        .pathParam("username",username)
                .when()
                        .put(Routes.put_user_url);

        return response;
    }

    public static Response deleteUser(Object userName)
    {
        Response response=
                given().filter(new AllureRestAssured())
                        .accept(ContentType.JSON)
                        .pathParam("username",userName.toString())
                .when()
                        .delete(Routes.delete_user_url);
        return response;
    }


}
