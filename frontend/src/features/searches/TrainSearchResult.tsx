import React, { useMemo, useState } from 'react';
import useSWR from 'swr';

import fetcher from '@/api/fetcher';
import Error from '@/components/layout/Error';
import ENDPOINT from '@/constants/Endpoint';
import TrainNoResults from '@/features/searches/TrainNotResults';
import TrainSelectItem from '@/features/searches/TrainSelectItem';
import type { Station } from '@/features/searches/types/Station';
import type { Train } from '@/features/searches/types/Train';

function TrainSearchResult() {
    const today = new Date();
    const dateStr = (date: Date) => {
        return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
    };
    const [date, setDate] = useState<string>(dateStr(today));
    const [searchDate, setSearchDate] = useState<string>(dateStr(today));
    const [searchDepartureStationId, setSearchDepartureStationId] =
        useState<string>('');
    const [searchArrivalStationId, setSearchArrivalStationId] =
        useState<string>('');

    const { data: stations, error: stationError } = useSWR<Station[]>(
        ENDPOINT.STATIONS,
        fetcher,
    );
    const { data: departureStations = stations } = useSWR<Station[]>(
        searchArrivalStationId
            ? ENDPOINT.REACHABLE_STATIONS(searchArrivalStationId)
            : null,
        fetcher,
    );
    const { data: arrivalStations = stations } = useSWR<Station[]>(
        searchDepartureStationId
            ? ENDPOINT.REACHABLE_STATIONS(searchDepartureStationId)
            : null,
        fetcher,
    );
    const [searchTime, setSearchTime] = useState<string>('');
    const [dateValidateError, setDateValidateError] = useState<string>();

    const { data: trains, error: trainError } = useSWR<Train[]>(
        dateValidateError
            ? null
            : ENDPOINT.TRAINS(
                  searchDepartureStationId,
                  searchArrivalStationId,
                  searchDate,
              ),
        fetcher,
    );

    const maxDate = () => {
        const date = new Date(
            today.getFullYear(),
            today.getMonth() + 1,
            today.getDate() - 1,
        );
        return dateStr(date);
    };

    const handleNextDateSearch = () => {
        const nextDate = new Date(date);
        nextDate.setDate(nextDate.getDate() + 1);
        const nextDateToString = dateStr(nextDate);
        setDate(nextDateToString);
        setSearchDate(nextDateToString);
    };

    const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setDate(e.target.value);

        const selectedDate = new Date(e.target.value);
        if (!(selectedDate instanceof Date) || isNaN(selectedDate.getTime())) {
            setDateValidateError('出発日を入力してください');
            return;
        }

        const today = new Date();
        today.setHours(0, 0, 0, 0);

        const maxDate = new Date(today);
        maxDate.setMonth(maxDate.getMonth() + 1);
        if (selectedDate < today || selectedDate > maxDate) {
            setDateValidateError(
                '出発日は本日から1か月以内の日付を指定してください',
            );
            return;
        }
        setDateValidateError('');
        setSearchDate(e.target.value);
    };

    const handleDepartureStationChenge = (
        e: React.ChangeEvent<HTMLSelectElement>,
    ) => {
        setSearchDepartureStationId(e.target.value);
    };

    const handleArrivalStationChenge = (
        e: React.ChangeEvent<HTMLSelectElement>,
    ) => {
        setSearchArrivalStationId(e.target.value);
    };

    const handleTimeChenge = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTime(e.target.value);
    };

    const filteredTrains = useMemo(() => {
        if (dateValidateError) {
            return [];
        }
        if (!searchTime) {
            return trains;
        }
        return trains?.filter((train) => {
            const departureSchedule = train.schedules.find(
                (schedule) => schedule.stationId === searchDepartureStationId,
            );
            if (!departureSchedule?.departureTime) {
                return false;
            }
            return departureSchedule.departureTime >= searchTime;
        });
    }, [
        trains,
        searchDepartureStationId,
        searchArrivalStationId,
        searchTime,
        dateValidateError,
    ]);

    if (stationError || trainError || !stations) {
        return <Error />;
    }

    return (
        <div className="mx-auto flex max-w-4xl flex-col gap-4 p-4">
            <div className="bg-primary/10 flex gap-4 rounded-2xl p-4">
                <div className="flex w-full flex-col gap-4">
                    <div className="flex gap-4">
                        <div className="flex w-full flex-col">
                            <h5>乗車駅</h5>
                            <select
                                className="border-primary/20 rounded-lg border bg-white px-4 py-2"
                                onChange={handleDepartureStationChenge}
                                value={searchDepartureStationId}
                                title="乗車駅を選択してください"
                            >
                                <option hidden>乗車駅を選択してください</option>

                                {(departureStations ?? stations)?.map(
                                    (station) => (
                                        <option
                                            key={station.id}
                                            value={station.id}
                                        >
                                            {station.stationName}
                                        </option>
                                    ),
                                )}
                            </select>
                        </div>
                        <div className="flex w-full flex-col">
                            <h5>降車駅</h5>
                            <select
                                className="border-primary/20 rounded-lg border bg-white px-4 py-2"
                                onChange={handleArrivalStationChenge}
                                value={searchArrivalStationId}
                                title="降車駅を選択してください"
                            >
                                <option hidden>降車駅を選択してください</option>

                                {(arrivalStations ?? stations)?.map(
                                    (station) => (
                                        <option
                                            key={station.id}
                                            value={station.id}
                                        >
                                            {station.stationName}
                                        </option>
                                    ),
                                )}
                            </select>
                        </div>
                    </div>
                    <div className="flex gap-4">
                        <div className="flex w-full flex-col">
                            <h5>出発日</h5>
                            <input
                                className="border-primary/20 rounded-lg border bg-white px-4 py-2"
                                type="date"
                                title="日付を選択してください"
                                value={date}
                                onChange={handleDateChange}
                                required
                                min={dateStr(today)}
                                max={maxDate()}
                            />

                            {dateValidateError && (
                                <p className="mt-1 text-sm text-red-600">
                                    {dateValidateError}
                                </p>
                            )}
                        </div>
                        <div className="flex w-full flex-col">
                            <h5>出発時刻</h5>
                            <input
                                className="border-primary/20 rounded-lg border bg-white px-4 py-2"
                                type="Time"
                                title="時刻を選択してください"
                                value={searchTime}
                                onChange={handleTimeChenge}
                                required
                            />
                        </div>
                    </div>
                </div>
            </div>
            <div className="flex justify-end">
                <h5>
                    {filteredTrains ? filteredTrains.length : 0}
                    件の列車が見つかりました
                </h5>
            </div>
            {filteredTrains && filteredTrains.length > 0 ? (
                filteredTrains.map((train) => (
                    <TrainSelectItem
                        key={train.id}
                        train={train}
                        departureStation={
                            stations.find(
                                (sta) => sta.id == searchDepartureStationId,
                            ) ?? stations[0]
                        }
                        arrivalStation={
                            stations.find(
                                (sta) => sta.id == searchArrivalStationId,
                            ) ?? stations[0]
                        }
                        departureDate={date}
                    />
                ))
            ) : (
                <TrainNoResults handleNextDateSearch={handleNextDateSearch} />
            )}
        </div>
    );
}

export default TrainSearchResult;
