import { useState } from 'react';
import useSWR from 'swr';

import fetcher from '@/api/fetcher';
import Error from '@/components/layout/Error';
import ENDPOINT from '@/constants/Endpoint';
import TrainNoResults from '@/features/searches/TrainNotResults';
import TrainSelectItem from '@/features/searches/TrainSelectItem';
import type { Train } from '@/features/searches/types/Train';

function TrainSearchResult() {
    const TOKYO_STATION_ID = '00000000';
    const UENO_STATION_ID = '00000001';
    const [date, setDate] = useState<string>(
        new Date().toISOString().split('T')[0],
    );
    const [searchDate, setSearchDate] = useState<string>(
        new Date().toISOString().split('T')[0],
    );
    const { data: trains, error } = useSWR<Train[]>(
        ENDPOINT.TRAINS(TOKYO_STATION_ID, UENO_STATION_ID, searchDate),
        fetcher,
    );

    const [dateValidateError, setDateValidateError] = useState<string>();

    const handleNextDateSearch = () => {
        const nextDate = new Date(date);
        nextDate.setDate(nextDate.getDate() + 1);
        const nextDateToString = nextDate.toISOString().split('T')[0];
        setDate(nextDateToString);
        setSearchDate(nextDateToString);
    };

    const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setDate(e.target.value);

        const selectedDate = new Date(e.target.value);

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

    if (error) {
        return <Error />;
    }

    return (
        <div className="mx-auto flex max-w-4xl flex-col gap-4 p-4">
            <h1>東京→上野</h1>
            <div className="bg-primary/10 flex justify-between rounded-2xl p-4">
                <div>
                    <h5>出発日</h5>
                    <input
                        className="border-primary/20 rounded-lg border bg-white px-4 py-2"
                        type="date"
                        title="日付を選択してください"
                        value={date}
                        onChange={handleDateChange}
                        required
                    />

                    {dateValidateError && (
                        <p className="mt-1 text-sm text-red-600">
                            {dateValidateError}
                        </p>
                    )}
                </div>
                <button
                    className="contained_btn flex items-center"
                    onClick={() => setSearchDate(date)}
                >
                    <span className="material-symbols-outlined">search</span>
                    日付指定
                </button>
            </div>
            <div className="flex justify-end">
                <h5>{trains ? trains.length : 0}件の列車が見つかりました</h5>
            </div>
            {trains && trains.length > 0 ? (
                trains.map((train) => (
                    <TrainSelectItem
                        key={train.id}
                        train={train}
                        departureStationId={TOKYO_STATION_ID}
                        arrivalStationId={UENO_STATION_ID}
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
