package core.service.maximo;

import core.service.utils.GetProperties;
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
public class GetSrs {

    public static String getDataFromMaximo() throws IOException {
        String srinfo;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(GetProperties.properies("host") + "mxsr?status=~eq~INPROG&_format=json");
        request.addHeader("Authorization", GetProperties.properies("auth"));
        HttpResponse httpResponse = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        JSONObject rsCount = jsonObject.getJSONObject("QueryMXSRResponse");
        if (rsCount.getInt("rsCount") > 0) {
            srinfo = "Заявки в работе: " + "\n";
            JSONArray srlist = jsonObject.getJSONObject("QueryMXSRResponse").getJSONObject("MXSRSet").getJSONArray("SR");
            for (int i = 0; i < srlist.length(); i++) {
                JSONObject elem1 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("TICKETID");
                JSONObject elem2 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("DESCRIPTION");
                srinfo = srinfo + elem1.getString("content") + " - " + elem2.getString("content") + "\n";
            }
        } else {
            srinfo = "Заявок в работе нет.";
        }
        return srinfo;
    }
}
