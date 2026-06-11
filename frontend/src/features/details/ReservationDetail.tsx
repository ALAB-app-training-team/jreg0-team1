import { useParams, useNavigate, useLocation } from "react-router-dom";
import useSWR from "swr";
import type { Detail } from "@/features/details/types/Detail";
import ENDPOINT from "@/constants/Endpoint";
import fetcher from "@/api/fetcher";

function ReservationDetail() {
     const testData: Detail = {
    id: "testId",
    trainName: "hayabusa-0",
    trainNickName: "はやぶさ",
    reservationDate: "2026-06-03",
    departureDate: "2026-06-03",
    seatLocation: "13C",
    carNumber: 9,
    seatType: "Gran",
    departureStationName: "東京",
    departureTime: "06:08",
    arrivalStationName: "上野",
    arrivalTime: "06:13",
    departureTrack: 20,
    };  // テスト
    const { id } = useParams();
    const navigate = useNavigate();

    let { data: details, error } = useSWR<Detail>(
        id ? ENDPOINT.DETAILS(id) : null, fetcher
    );
    if(!details) details = testData;
    const departureDate = new Date(details.departureDate);

    if(!id || error) return <h1>エラーが発生しました。しばらくしてから再度お試しください。</h1>
    if(!details) return <h1>Now Loading...</h1>

    return (
        <>
            <div className="max-w-4xl mx-auto flex flex-col gap-4">
                <button className="flex" 
                    onClick={()=> navigate("/reservationComplete", {state: {reservationId: id}})}>
                    <span className="material-symbols-outlined">arrow_back</span>  戻る
                </button>
                <h1>
                    予約確認
                </h1>
                <div className="flex flex-col border border-primary/20 rounded-2xl p-8">
                    <h1>
                        {details.trainNickName}
                    </h1>
                    <h3>
                        {details.trainName.split("-")[1]}号
                    </h3>
                </div>
                <div className="flex flex-col border border-primary/20 rounded-2xl p-8 gap-6">
                    <div className="flex">
                        <div className="flex flex-col grow-2">
                            <h5>
                                出発
                            </h5>
                            <h1>
                                {details.departureTime}
                            </h1>
                            <h3>
                                {details.departureStationName}
                            </h3>
                        </div>
                        <div className="flex flex-col grow-2">
                            <h5>
                                到着
                            </h5>
                            <h1>
                                {details.arrivalTime}
                            </h1>
                            <h3>
                                {details.arrivalStationName}
                            </h3>
                        </div>
                    </div>
                    <div className="flex flex-col gap-2">
                        <div className="flex gap-2">
                            <span className="material-symbols-outlined">location_on</span>
                            {details.departureTrack}番線
                        </div>
                        <div className="flex gap-2">
                            <span className="material-symbols-outlined">schedule</span>
                            {departureDate.getFullYear()}年
                            {departureDate.getMonth()+1}月
                            {departureDate.getDate()}日
                        </div>
                    </div>
                    <div className="flex flex-col self-start gap-2">
                        <h5>座席</h5>
                        <div className="flex flex-none bg-primary/20 text-primary rounded-2xl p-2">
                            <span className="material-symbols-outlined">train</span>
                            {details.carNumber}号車
                            {details.seatLocation}
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default ReservationDetail;