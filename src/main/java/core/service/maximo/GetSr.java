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
public class GetSr {
    public static String getSrDetail(String ticketid) throws IOException {
        String srdetail = "";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(GetProperties.properies("host") + "mxsr?ticketid=~eq~" + ticketid + "&_format=json");
        request.addHeader("Authorization", GetProperties.properies("auth"));
        HttpResponse httpResponse = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        JSONObject rsCount = jsonObject.getJSONObject("QueryMXSRResponse");
        if (rsCount.getInt("rsCount") > 0) {
            JSONArray srlist = jsonObject.getJSONObject("QueryMXSRResponse").getJSONObject("MXSRSet").getJSONArray("SR");
            for (int i = 0; i < srlist.length(); i++) {
                JSONObject elem1 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("TICKETID");
                JSONObject elem2 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("DESCRIPTION");
                JSONObject elem3 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("DESCRIPTION_LONGDESCRIPTION");
                JSONArray personlist = srlist.getJSONObject(i).getJSONObject("RelatedMbos").getJSONArray("PERSON");
                JSONArray status = srlist.getJSONObject(i).getJSONObject("RelatedMbos").getJSONArray("SYNONYMDOMAIN");
                JSONArray lstatus = status.getJSONObject(0).getJSONObject("RelatedMbos").getJSONArray("L_SYNONYMDOMAIN");
                JSONObject elem6 = personlist.getJSONObject(0).getJSONObject("Attributes").getJSONObject("DISPLAYNAME");
                JSONObject elem7 = lstatus.getJSONObject(0).getJSONObject("Attributes").getJSONObject("DESCRIPTION");
                String strippedText = elem3.getString("content").replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                String subStr = strippedText.codePointCount(0, strippedText.length()) > 3500 ?
                        strippedText.substring(0, strippedText.offsetByCodePoints(0, 3500)) : strippedText;
                srdetail = "Заявка №" + elem1.getString("content") + "\n" +
                        "Тема - " + elem2.getString("content") + "\n" +
                        "Описание - " + subStr + "\n" +
                        "Статус - " + elem7.getString("content") + "\n" +
                        "Инициатор - " + elem6.getString("content") + "\n" +
                        "Ссылка на заявку - " + GetProperties.properies("ticketlink") + elem1.getString("content");
            }
        } else {
            srdetail = "Заявка с номером - " +
                    ticketid +
                    " не найдена в системе";
        }
        return srdetail;
    }
}
