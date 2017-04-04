package core.service.maximo;

import core.service.utils.GetProperties;
import core.service.utils.SrMap;
import core.service.utils.UsersMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by astegnienko on 04.04.2017.
 */
public class NewSr {
    public static String createSR(String description, String longdesc, String personid) throws IOException {
        String ticketid = "";
        HttpClient httpClient = HttpClientBuilder.create().build();
        description = description.replaceAll(" ", "%20");
        longdesc = longdesc.replaceAll(" ", "%20");
        HttpPut request = new HttpPut(GetProperties.properies("host") + "mxsr?description=" + description + "&description_longdescription=" + longdesc + "&reportedby=" + UsersMap.getUSERS().get(personid) + "&_format=json");
        request.addHeader("Authorization", GetProperties.properies("auth"));
        HttpResponse httpResponse = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        JSONObject rsCount = jsonObject.getJSONObject("CreateMXSRResponse");
        if (rsCount.getInt("rsCount") > 0) {
            JSONObject srlist = jsonObject.getJSONObject("CreateMXSRResponse").getJSONObject("MXSRSet").getJSONObject("SR");
            JSONObject elem1 = srlist.getJSONObject("Attributes").getJSONObject("TICKETID");
            ticketid = "Зарегистрирована новая заявка - " + elem1.getString("content") + "\n" +
                    "Ссылка на заявку - " + GetProperties.properies("ticketlink") + elem1.getString("content");
            SrMap.myHashMap.remove(personid);
        }
        return ticketid;
    }
}
