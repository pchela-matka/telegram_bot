package core.service.utils;

/**
 * Created by astegnienko on 20.04.2017.
 */
public class CleanHashMap {

    public static void CleanInfo(String personid) {
        SrMap.myHashMap.remove(personid);
        FeedbackMap.FEEDBACK.remove(personid);
    }
}
