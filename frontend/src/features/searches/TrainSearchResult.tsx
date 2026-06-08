import { useState } from "react";
import useSWR from "swr";
import fetcher from "@/api/fetcher";
import ENDPOINT from "@/constants/Endpoint";
import type { Train } from "@/features/searches/types/Train";
import TrainSelectItem from "@/features/searches/TrainSelectItem";
import TrainNoResults from "@/features/searches/TrainNotResults";

function TrainSearchResult(){
    const TOKYO_STATION_ID = "00000000";
    const UENO_STATION_ID = "00000001";
    const [date, setDate] = useState<string>(new Date().toISOString().split("T")[0]);
    const [searchDate, setSearchDate] = useState<string>(new Date().toISOString().split("T")[0]);
    const {data: trains, error} = useSWR<Train[]>(ENDPOINT.TRAINS(TOKYO_STATION_ID,UENO_STATION_ID, searchDate), fetcher);

    const handleNextDateSearch = () => {
         const nextDate = new Date(date);
         nextDate.setDate(nextDate.getDate()+1);
         const nextDateToString = nextDate.toISOString().split("T")[0]
         setDate(nextDateToString);
         setSearchDate(nextDateToString);
    }

    // if(error){
    //     return (<h1>エラーが発生しました。しばらくしてから再度お試しください。</h1>)
    // }

    return (
    <div className="p-4 flex flex-col gap-4">
        <h1>
            東京→上野
        </h1>
        <div className="rounded-2xl bg-primary/[10%] p-4 flex justify-between">
            <div>
                <h5>出発日</h5>
                <input 
                className="bg-white border border-primary/[20%] rounded-lg px-4 py-2"
                type="date"
                title="日付を選択してください"
                value={date}
                onChange={e => setDate(e.target.value)} 
                required/>
            </div>
            <button className="contained_btn flex items-center"
            onClick={() => setSearchDate(date)}>
                <span className="material-symbols-outlined">search</span>
                日付指定
            </button>
        </div>
        <div className="flex justify-end">
            <h5>
                {trains ? trains.length : 0}件の列車が見つかりました
            </h5>
        </div>
        {
            trains
            ? trains.map(train => (
                <TrainSelectItem
                train={train}
                departureStationId={TOKYO_STATION_ID}
                arrivalStationId={UENO_STATION_ID}/>
            ))
            : <TrainNoResults
            handleNextDateSearch={handleNextDateSearch}/>
        }
    </div>
    );
};

export default TrainSearchResult;
