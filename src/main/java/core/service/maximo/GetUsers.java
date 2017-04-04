package core.service.maximo;

import core.service.utils.GetProperties;
import core.service.utils.UsersMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by astegnienko on 04.04.2017.
 */
public class GetUsers {
    public static void getTelegramUsers() throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(GetProperties.properies("host") + "mxperson?telegramid=~neq~0" + "&_format=json&_compact=true");
        request.addHeader("Authorization", GetProperties.properies("auth"));
        HttpResponse httpResponse = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        JSONObject rsCount = jsonObject.getJSONObject("QueryMXPERSONResponse");
        if (rsCount.getInt("rsCount") > 0) {
            JSONArray srlist = jsonObject.getJSONObject("QueryMXPERSONResponse").getJSONObject("MXPERSONSet").getJSONArray("PERSON");
            for (int i = 0; i < srlist.length(); i++) {
                UsersMap.getUSERS().put(String.valueOf(srlist.getJSONObject(i).getInt("TELEGRAMID")),
                        srlist.getJSONObject(i).getString("PERSONID"));
            }
        }
    }
}
