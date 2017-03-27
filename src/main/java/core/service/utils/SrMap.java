package core.service.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by astegnienko on 01.03.2017.
 */
public class SrMap {

    public static Map<String, MyEntity> myHashMap = new HashMap<String, MyEntity>();

    public static Map<String, MyEntity> getMyHashMap() {
        return myHashMap;
    }
}
