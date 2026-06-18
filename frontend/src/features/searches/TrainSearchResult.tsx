import { useEffect, useState } from 'react';
import useSWR from 'swr';
import useSWRMutation from 'swr/mutation';

import fetcher from '@/api/fetcher';
import Error from '@/components/layout/Error';
import ENDPOINT from '@/constants/Endpoint';
import TrainNoResults from '@/features/searches/TrainNotResults';
import TrainSelectItem from '@/features/searches/TrainSelectItem';
import type { Station } from '@/features/searches/types/Station';
import type { Train } from '@/features/searches/types/Train';

function TrainSearchResult() {
    const [date, setDate] = useState<string>(
        new Date().toISOString().split('T')[0],
    );
    const [searchDate, setSearchDate] = useState<string>(
        new Date().toISOString().split('T')[0],
    );
    const [searchDepartureStationId, setSearchDepaertureStationId] =
        useState<string>('');
    const [searchArrivalStationId, setSelectedArrivalStationId] =
        useState<string>('');
    const { data: stations, error: stationError } = useSWR<Station[]>(
        ENDPOINT.STATIONS,
        fetcher,
    );
    const [displayStation, setDisplayStation] = useState<{
        departureStation: Station;
        arrivalStation: Station;
    }>({
        departureStation: { id: '', stationName: '' },
        arrivalStation: { id: '', stationName: '' },
    });

    useEffect(() => {
        if (stations && stations.length > 0) {
            setDisplayStation({
                departureStation:
                    stations.find(
                        (sta) => sta.id == searchDepartureStationId,
                    ) ?? stations[0],
                arrivalStation:
                    stations.find((sta) => sta.id == searchArrivalStationId) ??
                    stations[0],
            });
        }
    });
    const {
        data: trains,
        trigger,
        isMutating: ismutating,
        error: trainError,
    } = useSWRMutation<Train[]>(
        ENDPOINT.TRAINS(
            searchDepartureStationId,
            searchArrivalStationId,
            searchDate,
        ),
        fetcher,
    );

    const handleNextDateSearch = () => {
        const nextDate = new Date(date);
        nextDate.setDate(nextDate.getDate() + 1);
        const nextDateToString = nextDate.toISOString().split('T')[0];
        setDate(nextDateToString);
        setSearchDate(nextDateToString);
    };

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
                                onChange={(e) =>
                                    setSearchDepaertureStationId(e.target.value)
                                }
                                defaultValue={stations[0].id}
                            >
                                {stations.map((station) => (
                                    <option key={station.id} value={station.id}>
                                        {station.stationName}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="flex w-full flex-col">
                            <h5>降車駅</h5>
                            <select
                                className="border-primary/20 rounded-lg border bg-white px-4 py-2"
                                onChange={(e) =>
                                    setSelectedArrivalStationId(e.target.value)
                                }
                            >
                                {stations.map((station) => (
                                    <option key={station.id} value={station.id}>
                                        {station.stationName}
                                    </option>
                                ))}
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
                                onChange={(e) => setDate(e.target.value)}
                                required
                            />
                        </div>
                        <div className="flex w-full flex-col">
                            <h5>出発時刻</h5>
                            <input
                                className="border-primary/20 rounded-lg border bg-white px-4 py-2"
                                type="Time"
                                title="時刻を選択してください"
                                // value={}
                                // onChange={}
                                required
                            />
                        </div>
                    </div>
                </div>
                <div className="ml-auto shrink-0 place-self-end">
                    <button
                        className="contained_btn flex items-center"
                        onClick={() => {
                            trigger();
                        }}
                        disabled={ismutating}
                    >
                        <span className="material-symbols-outlined">
                            search
                        </span>
                        検索
                    </button>
                </div>
            </div>
            <div className="flex">
                {trains && displayStation && (
                    <h1>
                        {displayStation.departureStation.stationName}
                        <span className="material-symbols-outlined">
                            arrow_forward
                        </span>
                        {displayStation.arrivalStation.stationName}
                    </h1>
                )}
            </div>
            <div className="flex justify-end">
                <h5>{trains ? trains.length : 0}件の列車が見つかりました</h5>
            </div>
            {trains && trains.length > 0 ? (
                trains.map((train) => (
                    <TrainSelectItem
                        key={train.id}
                        train={train}
                        departureStation={displayStation.departureStation}
                        arrivalStation={displayStation.arrivalStation}
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
