package com.example.SerialMap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ���л�map��Bundle����mapʹ��
 * 
 */
public class SerializableMap implements Serializable {
 
    private HashMap<String, Object>map;
    private Map<String, Object>maps;
 
    public HashMap<String, Object> getMap() {
        return map;
    }
 
    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }
    public Map<String, Object> getMaps() {
        return maps;
    }
 
    public void setMaps(Map<String, Object> map) {
        this.maps = map;
    }
}