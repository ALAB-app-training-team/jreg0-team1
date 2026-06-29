import { QRCodeSVG } from 'qrcode.react';
import { useNavigate, useParams } from 'react-router-dom';
import useSWR from 'swr';

import fetcher from '@/api/fetcher';
import favicon from '@/assets/favicon.png';
import Error from '@/components/layout/Error';
import Loading from '@/components/layout/Loading';
import ENDPOINT from '@/constants/Endpoint';
import type { ReservationDetail } from '@/features/reservations/types/ReservationDetail';

function ReservationInfo() {
    const { id } = useParams();
    const navigate = useNavigate();

    const { data: details, error } = useSWR<ReservationDetail>(
        id ? ENDPOINT.DETAILS(id) : null,
        fetcher,
    );

    if (!id || error) return <Error />;
    if (!details) return <Loading />;

    const [year, month, day] = details.departureDate.split('-');

    return (
        <>
            <div className="mx-auto flex max-w-4xl flex-col gap-4">
                <button
                    className="mt-4 flex cursor-pointer gap-4 p-2 hover:text-black/50"
                    onClick={() => navigate('/')}
                >
                    <span className="material-symbols-outlined">
                        arrow_back
                    </span>
                    検索画面に戻る
                </button>
                <div className="border-primary/20 flex flex-col items-center gap-4 rounded-2xl border p-8 text-center">
                    <div>
                        <h1>{details.trainNickname}</h1>
                        <h3>{details.trainName.split('-')[1]}号</h3>
                    </div>
                    <QRCodeSVG
                        value={'jreg0-' + details.id}
                        size={200}
                        level="H"
                        title="QRCode"
                        imageSettings={{
                            src: favicon,
                            height: 56,
                            width: 56,
                            excavate: false,
                        }}
                    />
                    <h5>
                        予約番号
                        <br />
                        {details.id}
                    </h5>
                </div>
                <div className="border-primary/20 flex flex-col gap-6 rounded-2xl border p-8">
                    <div className="flex">
                        <div className="flex grow-2 flex-col">
                            <h5>出発</h5>
                            <h1>{details.departureTime.slice(0, 5)}</h1>
                            <h3>{details.departureStationName}</h3>
                        </div>
                        <div className="flex grow-2 flex-col">
                            <h5>到着</h5>
                            <h1>{details.arrivalTime.slice(0, 5)}</h1>
                            <h3>{details.arrivalStationName}</h3>
                        </div>
                    </div>
                    <div className="flex flex-col gap-2">
                        <div className="flex gap-2">
                            <span className="material-symbols-outlined">
                                location_on
                            </span>
                            {details.departureTrack}番線
                        </div>
                        <div className="flex gap-2">
                            <span className="material-symbols-outlined">
                                schedule
                            </span>
                            {year}年{parseInt(month, 10)}月{parseInt(day, 10)}日
                        </div>
                    </div>
                    <div className="flex flex-col gap-2 self-start">
                        <h5>座席</h5>
                        <div className="bg-primary/20 text-primary flex flex-none rounded-2xl p-2">
                            <span className="material-symbols-outlined">
                                train
                            </span>
                            {details.carNumber}号車
                            {details.seatLocation}
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ReservationInfo;
