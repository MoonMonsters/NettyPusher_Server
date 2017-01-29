package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;
import java.util.Map;

public class SerializableMap implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Integer, Integer> map;

    public SerializableMap() {
    }

    public SerializableMap(Map<Integer, Integer> map) {
        this.map = map;
    }

    public Map<Integer, Integer> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Integer> map) {
        this.map = map;
    }
}