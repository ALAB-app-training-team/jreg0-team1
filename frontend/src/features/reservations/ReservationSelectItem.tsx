import { useNavigate } from 'react-router-dom';

import type { ReservationDetail } from '@/features/reservations/types/ReservationDetail';

type ReservationSelectItemProps = {
    details: ReservationDetail;
};

function ReservationSelectItem({ details }: ReservationSelectItemProps) {
    const [year, month, day] = details.departureDate.split('-');
    const navigate = useNavigate();

    const departureDate = new Date(details.departureDate);
    const now = new Date();

    return (
        <div className="border-primary/20 flex flex-col gap-4 rounded-2xl border p-8">
            <div className="flex-col">
                <div className="flex">
                    <div className="flex grow-2 items-center gap-2 font-semibold">
                        <span className="material-symbols-outlined">
                            confirmation_number
                        </span>
                        <h3>
                            {details.trainName.split('-')[1]}号 -{' '}
                            {details.trainNickname}
                        </h3>
                    </div>
                    {departureDate >= now && (
                        <div className="bg-primary right-0 flex items-center justify-center rounded-xl px-3 text-sm text-white">
                            有効
                        </div>
                    )}
                </div>
                <div className="flex py-2">
                    <h5>
                        {details.departureStationName} →{' '}
                        {details.arrivalStationName}
                    </h5>
                </div>
            </div>
            <div className="flex">
                <div className="flex w-full flex-col">
                    <h5>出発</h5>
                    <h3 className="font-semibold">
                        {year}年{parseInt(month, 10)}月{parseInt(day, 10)}日
                        {` `}
                        {details.departureTime.slice(0, 5)}
                    </h3>
                </div>
                <div className="flex w-full flex-col">
                    <h5>ホーム</h5>
                    <h3 className="font-semibold">
                        {details.departureTrack}番線
                    </h3>
                </div>
            </div>
            <div className="flex">
                <div className="flex flex-col gap-2 self-start">
                    <h5>座席</h5>
                    <div className="border-primary/20 flex flex-none rounded-2xl border px-2 py-1 font-semibold">
                        {details.carNumber}号車 {details.seatLocation}
                    </div>
                </div>
            </div>
            <button
                onClick={() => navigate(`/details/${details.id}`)}
                className="contained_btn right-0 flex cursor-pointer items-center justify-center gap-2"
            >
                <span className="material-symbols-outlined">qr_code_2</span>
                チケットを表示
            </button>
        </div>
    );
}

export default ReservationSelectItem;
