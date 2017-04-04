package core.service.maximo;

import core.service.utils.GetProperties;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by astegnienko on 04.04.2017.
 */
public class GetPersonMaximo {
    public static boolean getPersonByID(Integer id) throws IOException {
        boolean person = true;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(GetProperties.properies("host") + "mxperson?telegramid=~eq~" + id + "&_format=json");
        request.addHeader("Authorization", GetProperties.properies("auth"));
        HttpResponse httpResponse = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        JSONObject rsCount = jsonObject.getJSONObject("QueryMXPERSONResponse");
        if (rsCount.getInt("rsCount") > 0)
            person = false;
        return person;
    }
}
