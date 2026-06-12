import { useParams, useNavigate } from "react-router-dom";
import useSWR from "swr";
import type { ReservationDetail } from "@/features/details/types/ReservationDetail";
import ENDPOINT from "@/constants/Endpoint";
import fetcher from "@/api/fetcher";
import Error from "@/components/layout/Error"
import { QRCodeSVG } from "qrcode.react";
import favicon from "@/assets/favicon.png"

function ReservationInfo() {
    const { id } = useParams();
    const navigate = useNavigate();

    let { data: details, error } = useSWR<ReservationDetail>(
        id ? ENDPOINT.DETAILS(id) : null, fetcher
    );

    if(!id || error) return <Error />
    if(!details) return <h1>Now Loading...</h1>

    return (
        <>
            <div className="max-w-4xl mx-auto flex flex-col gap-4">
                <button className="flex gap-4 hover:text-black/50 mt-4 p-2" 
                    onClick={()=> navigate("/")}>
                    <span className="material-symbols-outlined">arrow_back</span>検索画面に戻る
                </button>
                <div className="flex flex-col border border-primary/20 rounded-2xl p-8 text-center items-center gap-4">
                    <div>
                        <h1>{details.trainNickname}</h1>
                        <h3>{details.trainName.split("-")[1]}号</h3>
                    </div>
                    <QRCodeSVG value={"jreg0-" + details.id} size={200} level="H" title="QRCode" imageSettings={{src:favicon, height:56, width:56, excavate: false}}/>
                    <h5>予約番号<br/>{details.id}</h5>
                    <button>

                    </button>
                </div>
                <div className="flex flex-col border border-primary/20 rounded-2xl p-8 gap-6">
                    <div className="flex">
                        <div className="flex flex-col grow-2">
                            <h5>出発</h5>
                            <h1>{details.departureTime.slice(0,5)}</h1>
                            <h3>{details.departureStationName}</h3>
                        </div>
                        <div className="flex flex-col grow-2">
                            <h5>到着</h5>
                            <h1>{details.arrivalTime.slice(0,5)}</h1>
                            <h3>{details.arrivalStationName}</h3>
                        </div>
                    </div>
                    <div className="flex flex-col gap-2">
                        <div className="flex gap-2">
                            <span className="material-symbols-outlined">location_on</span>
                            {details.departureTrack}番線
                        </div>
                        <div className="flex gap-2">
                            <span className="material-symbols-outlined">schedule</span>
                            {details.departureDate.split("T")[0].replaceAll("-","/")}
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

export default ReservationInfo;
