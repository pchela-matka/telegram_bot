package core.service;

import core.service.utils.SrMap;
import core.service.utils.UsersMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;

public class MaximoService {
    final static String MAXIMO_HOST = "http://192.168.70.229/maxrest/rest/os/";
//    public static void main(String[] args) throws IOException {
//        String srinfo = "Заявки: ";
//        String infosr = getDataFromMaximo(srinfo);
//        System.out.printf(infosr);
//    }

    public static String getDataFromMaximo() throws IOException {
        String srinfo;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(MAXIMO_HOST + "mxsr?status=~eq~INPROG&_format=json");
        //HttpGet request = new HttpGet("http://192.168.70.229/maxrest/rest/os/mxsr?_format=json&_maxItems=9");
        request.addHeader("Authorization", "Basic test");
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

/*    public static String getPersonFromMaximo(String phone) throws IOException {
        String fio;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut request1 = new HttpPut(MAXIMO_HOST + "mxperson/2216?displayname=123456");
        request1.addHeader("Authorization", "Basic bWF4YWRtaW46U3RyMG5nMW5nOTAoKQ==");
        HttpResponse httpResponse1 = httpClient.execute(request1);
        HttpGet request = new HttpGet(MAXIMO_HOST + "mxperson?phone.phonenum=~eq~" + "380506514674" + "&_format=json");
        //HttpGet request = new HttpGet("http://192.168.70.229/maxrest/rest/os/mxsr?_format=json&_maxItems=9");
        request.addHeader("Authorization", "Basic test");
        HttpResponse httpResponse = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        JSONObject rsCount = jsonObject.getJSONObject("QueryMXPERSONResponse");
        if (rsCount.getInt("rsCount") > 0) {
            fio = "Добрый день!" + "\n";
            JSONArray srlist = jsonObject.getJSONObject("QueryMXPERSONResponse").getJSONObject("MXPERSONSet").getJSONArray("PERSON");
            for (int i = 0; i < srlist.length(); i++) {
                JSONObject elem1 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("DISPLAYNAME");
                JSONObject elem2 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("PERSONUID");
                fio = fio + elem1.getString("content") + " " + elem2.getInt("content");
            }
        } else {
            fio = "Добрый день!" + "\n" + "Вы не зарегистрированы в системе. Обратитесь к администратору - Кострец МиниПожар Виталька";
        }
        return fio;
    }*/

    public static boolean getPersonByID(Integer id) throws IOException {
        boolean person = true;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(MAXIMO_HOST + "mxperson?telegramid=~eq~" + id + "&_format=json");
        request.addHeader("Authorization", "Basic test==");
        HttpResponse httpResponse = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        JSONObject rsCount = jsonObject.getJSONObject("QueryMXPERSONResponse");
        if (rsCount.getInt("rsCount") > 0)
            person = false;
        return person;
    }

    public static String addPersonPhone(String phone, Integer id) throws IOException {
        String fio;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(MAXIMO_HOST + "mxperson?phone.phonenum=~eq~" + phone + "&_format=json");
        request.addHeader("Authorization", "Basic test==");
        HttpResponse httpResponse = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        JSONObject rsCount = jsonObject.getJSONObject("QueryMXPERSONResponse");
        if (rsCount.getInt("rsCount") > 0) {
            fio = "Добрый день!" + "\n" + "Вас идентифицировали - ";
            JSONArray srlist = jsonObject.getJSONObject("QueryMXPERSONResponse").getJSONObject("MXPERSONSet").getJSONArray("PERSON");
            for (int i = 0; i < srlist.length(); i++) {
                JSONObject elem1 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("DISPLAYNAME");
                JSONObject elem2 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("PERSONUID");
                HttpPut request1 = new HttpPut(MAXIMO_HOST + "mxperson/" + elem2.getInt("content") + "?telegramid=" + id);
                request1.addHeader("Authorization", "Basic test==");
                httpClient.execute(request1);
                UsersMap.getUSERS().put(String.valueOf(id), String.valueOf(elem2.getInt("content")));
                fio = fio + elem1.getString("content") + "\n" +
                        "Для получения информации необходимо использовать следующие команды:" + "\n" +
                        "/opensr - перечень ваших открытых заявок" + "\n" +
                        "/opensr \"номер заявки\" - детальная информация по заявке. Пример команды - /opensr 1000" + "\n" +
                        "/createsr \"тема описание(без пробелов)\" - детальная информация по заявке. Пример команды - /createsr Тест Заявка_для_тестирования_телеграма" + "\n" +
                        "/help - перечень доступных команд";
                //  + "\n" + "Я пока знаю только одну команду." +"\n"+"Для получения списка открытых заявок необходимо набрать /sropen";
            }
        } else {
            fio = "Добрый день!" + "\n" + "Мы попытались идентифицировать вас по телефону - " + phone + " и к сожалению вы не зарегистрированы в системе. Обратитесь к администратору системы - vkostrets@sibis.com.ua.";
        }
        return fio;
    }

    public static String getSrDetail(String ticketid) throws IOException {
        String srdetail = "";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(MAXIMO_HOST + "mxsr?ticketid=~eq~" + ticketid + "&_format=json");
        request.addHeader("Authorization", "Basic test==");
        HttpResponse httpResponse = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        JSONObject rsCount = jsonObject.getJSONObject("QueryMXSRResponse");
        if (rsCount.getInt("rsCount") > 0) {
            //srdetail = "Заявка: " + "\n";
            JSONArray srlist = jsonObject.getJSONObject("QueryMXSRResponse").getJSONObject("MXSRSet").getJSONArray("SR");
            for (int i = 0; i < srlist.length(); i++) {
                JSONObject elem1 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("TICKETID");
                JSONObject elem2 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("DESCRIPTION");
                JSONObject elem3 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("DESCRIPTION_LONGDESCRIPTION");
               // JSONObject elem4 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("STATUS");
                //JSONObject elem5 = srlist.getJSONObject(i).getJSONObject("Attributes").getJSONObject("REPORTEDBY");
                JSONArray personlist = srlist.getJSONObject(i).getJSONObject("RelatedMbos").getJSONArray("PERSON");
                JSONArray status = srlist.getJSONObject(i).getJSONObject("RelatedMbos").getJSONArray("SYNONYMDOMAIN");
                JSONArray lstatus = status.getJSONObject(0).getJSONObject("RelatedMbos").getJSONArray("L_SYNONYMDOMAIN");
                JSONObject elem6 =  personlist.getJSONObject(0).getJSONObject("Attributes").getJSONObject("DISPLAYNAME");
                JSONObject elem7 = lstatus.getJSONObject(0).getJSONObject("Attributes").getJSONObject("DESCRIPTION");
                String strippedText = elem3.getString("content").replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                String subStr = strippedText.codePointCount(0, strippedText.length()) > 3500 ?
                        strippedText.substring(0, strippedText.offsetByCodePoints(0, 3500)) : strippedText;
                srdetail = "Заявка №" + elem1.getString("content") + "\n" +
                        "Тема - " + elem2.getString("content") + "\n" +
                        "Описание - " + subStr + "\n" +
                        "Статус - " + elem7.getString("content") + "\n" +
                        "Инициатор - " + elem6.getString("content") + "\n" +
                        "Ссылка на заявку - http://192.168.70.229/maximo/ui/maximo.jsp?event=loadapp&value=sr&additionalevent=useqbe&additionaleventvalue=ticketid=" + elem1.getString("content");
            }
        } else {
            srdetail = "Заявка с номером - " +
                    ticketid +
                    " не найдена в системе";
        }
        return srdetail;
    }

    public static String createSR(String description, String longdesc, String personid) throws IOException {
        String ticketid = "";
        HttpClient httpClient = HttpClientBuilder.create().build();
        description = description.replaceAll(" ","%20");
        longdesc = longdesc.replaceAll(" ","%20");
        HttpPut request = new HttpPut(MAXIMO_HOST + "mxsr?description=" + description + "&description_longdescription=" + longdesc + "&reportedby=" + UsersMap.getUSERS().get(personid) + "&_format=json");
        request.addHeader("Authorization", "Basic test==");
        HttpResponse httpResponse = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        JSONObject rsCount = jsonObject.getJSONObject("CreateMXSRResponse");
        if (rsCount.getInt("rsCount") > 0) {
            JSONObject srlist = jsonObject.getJSONObject("CreateMXSRResponse").getJSONObject("MXSRSet").getJSONObject("SR");
            JSONObject elem1 = srlist.getJSONObject("Attributes").getJSONObject("TICKETID");
            ticketid = "Зарегистрирована новая заявка - " + elem1.getString("content") + "\n" +
            "Ссылка на заявку - http://192.168.70.229/maximo/ui/maximo.jsp?event=loadapp&value=sr&additionalevent=useqbe&additionaleventvalue=ticketid=" + elem1.getString("content");
            SrMap.myHashMap.remove(personid);
        }
        return ticketid;
    }

    public static void getTelegramUsers() throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(MAXIMO_HOST + "mxperson?telegramid=~neq~0" + "&_format=json&_compact=true");
        request.addHeader("Authorization", "Basic test==");
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
