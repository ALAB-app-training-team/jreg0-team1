package com.example.jreg0.utils;

import java.util.*;

public class StopStationList {
    private final LinkedList<String> stationList;

    public StopStationList(LinkedList<String> stationList) {
        this.stationList = stationList;
    }

    /**
     * 停車する駅IDを持つ不変Listを返すgetterメソッド
     *
     * @return 停車する駅IDを到着順に持つLinkedList
     */
    public List<String> getList() {
        return Collections.unmodifiableList(stationList);
    }

    /**
     * 始発駅のIDを返す
     *
     * @return 始発駅の駅ID 駅がない場合は空
     */

    public Optional<String> getFirst() {
        return stationList.isEmpty()
                ? Optional.empty()
                : Optional.of(stationList.getFirst());
    }

    /**
     * 終点駅のIDを返す
     *
     * @return 終点駅の駅ID 駅がない場合は空
     */

    public Optional<String> getLast() {
        return stationList.isEmpty()
                ? Optional.empty()
                : Optional.of(stationList.getLast());
    }

    /**
     * 指定した駅の次の駅のIDを返す
     *
     * @param stationId 基準駅のID
     * @return 基準駅の次の駅のID
     */
    public Optional<String> getNext(String stationId) {
        ListIterator<String> iterator = stationList.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(stationId)) {
                return iterator.hasNext()
                        ? Optional.of(iterator.next())
                        : Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * 指定した駅の前の駅のIDを返す
     *
     * @param stationId 基準駅のID
     * @return 基準駅の次の駅のID
     */
    public Optional<String> getPrev(String stationId) {
        ListIterator<String> iterator = stationList.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(stationId)) {
                return iterator.hasPrevious()
                        ? Optional.of(iterator.previous())
                        : Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * 指定した2駅を含む2駅間の駅IDのリストを返す
     *
     * @param startStation 最初の駅
     * @param endStation   最後の駅
     * @return 最初の駅から最後の駅までの中に含まれる駅IDのリスト
     */
    public Optional<List<String>> getRange(String startStation, String endStation) {
        int startIndex = stationList.indexOf(startStation);
        int endIndex = stationList.indexOf(endStation);

        if (startIndex < 0 || endIndex < 0 || startIndex > endIndex) {
            return Optional.empty();
        }

        List<String> range = new LinkedList<>(stationList.subList(startIndex, endIndex + 1));
        return Optional.of(range);
    }
}
