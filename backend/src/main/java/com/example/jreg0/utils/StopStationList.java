package com.example.jreg0.utils;

import java.util.*;

public class StopStationList {
    private final LinkedList<String> stationList;

    public StopStationList(LinkedList<String> stationList) {
        this.stationList = stationList;
    }

    /**
     * 停車する駅IDを持つLinkedListを返すgetterメソッド
     *
     * @return 停車する駅IDを到着順に持つLinkedList
     */
    public List<String> getList() {
        return Collections.unmodifiableList(stationList);
    }
}
