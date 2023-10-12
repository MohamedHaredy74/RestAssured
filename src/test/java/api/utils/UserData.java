package api.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;


public class UserData {

    Object userDetails[];
    Object usernames[];


    @DataProvider(name = "user data payload")
    public Object[] readUserDetails() throws IOException, ParseException {

        JSONParser parser=new JSONParser();
        FileReader dataFile =new FileReader("src/test/java/api/utils/userDetails.json");
        Object object=parser.parse(dataFile);
        JSONArray array= (JSONArray) object;
        userDetails=new Object[array.size()];

        for (int i=0 ; i < array.size();i++)
        {
            JSONObject jsonObject= (JSONObject) array.get(i);
            userDetails[i]=jsonObject;

        }
        return userDetails;
        }

    @DataProvider(name = "usernames payload")
    public Object[] readUserNames() throws IOException, ParseException {

        JSONParser parser=new JSONParser();
        FileReader dataFile =new FileReader("src/test/java/api/utils/userDetails.json");
        Object object=parser.parse(dataFile);
        JSONArray array= (JSONArray) object;
        usernames=new Object[array.size()];

        for (int i=0 ; i < array.size();i++)
        {
            JSONObject jsonObject= (JSONObject) array.get(i);
            usernames[i]=jsonObject.get("username");

        }
        return usernames;
    }



}
