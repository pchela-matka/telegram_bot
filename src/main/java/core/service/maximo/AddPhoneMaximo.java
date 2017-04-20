package core.service.maximo;

import core.service.utils.GetProperties;
import core.service.utils.UsersMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by astegnienko on 04.04.2017.
 */
public class AddPhoneMaximo {

    public static String addPersonPhone(String phone, Integer id) throws IOException {
        String fio;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(GetProperties.properies("host") + "mxperson?phone.phonenum=~eq~" + phone + "&_format=json");
        request.addHeader("Authorization", GetProperties.properies("auth"));
        HttpResponse httpResponse = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        JSONObject rsCount = jsonObject.getJSONObject("QueryMXPERSONResponse");
        if (rsCount.getInt("rsCount") > 0) {
            fio = "Добрый день!" + "\n" + "Вас идентифицировали - ";
            JSONArray srlist = jsonObject.getJSONObject("QueryMXPERSONResponse").getJSONObject("MXPERSONSet").getJSONArray("PERSON");
            for (int i = 0; i < srlist.length(); i++) {
                JSONObject elem1 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("DISPLAYNAME");
                JSONObject elem2 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("PERSONUID");
                HttpPut request1 = new HttpPut(GetProperties.properies("host") + "mxperson/" + elem2.getInt("content") + "?telegramid=" + id);
                request1.addHeader("Authorization", GetProperties.properies("auth"));
                httpClient.execute(request1);
                UsersMap.getUSERS().put(String.valueOf(id), String.valueOf(elem2.getInt("content")));
                fio = fio + elem1.getString("content") + "\n" +
                        "Для получения информации необходимо использовать следующие команды:" + "\n" +
                        "/opensr - перечень ваших открытых заявок" + "\n" +
                        "/opensr \"номер заявки\" - детальная информация по заявке. Пример команды - /opensr 1000" + "\n" +
                        "/createsr - создание заявки." + "\n" +
                        "/help - перечень доступных команд";
            }
        } else {
            fio = "Добрый день!" + "\n" + "Мы попытались идентифицировать вас по телефону - " + phone + " и к сожалению вы не зарегистрированы в системе. Обратитесь к администратору системы - astegnienko@sibis.com.ua.";
        }
        return fio;
    }
}
