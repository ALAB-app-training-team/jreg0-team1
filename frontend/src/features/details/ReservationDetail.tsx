import { useState } from "react";
import { useParams } from "react-router-dom";
import useSWR from "swr";
import type { Detail } from "@/features/details/types/Detail";
import ENDPOINT from "@/constants/Endpoint";
import fetcher from "@/api/fetcher";

function ReservationDetail() {
    const { reservationId } = useParams();
    // const { data: details ,error } = useSWR<Detail>(
    //     reservationId ? ENDPOINT.DETAILS(reservationId) : null, fetcher);

    // if(reservationId || error) return <h1>エラーが発生しました。しばらくしてから再度お試しください。</h1>

    return (
        <div>
            <h1>
                予約確認
            </h1>
            <div className="rounded-2xl ">

            </div>
        </div>
    )
}

export default ReservationDetail;