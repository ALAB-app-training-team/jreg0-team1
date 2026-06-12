import { useNavigate } from 'react-router-dom';

import { type Reservation } from '@/features/reservations/types/Reservation';
import type { Train } from '@/features/searches/types/Train';
import usePostReservation from '@/hooks/usePostReservation';
import { httpMethod } from '@/types/HttpMethod';

type TrainSelectItemProps = {
    train: Train;
    departureStationId: string;
    arrivalStationId: string;
    departureDate: string;
};

function TrainSelectItem({
    train,
    departureStationId,
    arrivalStationId,
    departureDate,
}: TrainSelectItemProps) {
    const trainNumber: string = train.trainName.split('-')[1];
    const departureTime = train.schedules
        .find((s) => s.stationId === departureStationId)
        ?.departureTime.slice(0, 5);
    const arrivalTime = train.schedules
        .find((s) => s.stationId === arrivalStationId)
        ?.arrivalTime.slice(0, 5);
    const { trigger: postTrigger } = usePostReservation();
    const navigate = useNavigate();

    const handleReserveSeat = async () => {
        const reserveToPost: Partial<Reservation> = {
            seatId: '',
            reservationDate: new Date().toISOString().split('T')[0],
            departureDate: departureDate,
            trainId: train.id,
            departureStationId: departureStationId,
            arrivalStationId: arrivalStationId,
            paymentMethod: '',
            paymentStatus: '',
            fee: 2600,
            accountId: '',
        };
        const data = await postTrigger({
            method: httpMethod.POST,
            body: reserveToPost,
        });

        navigate('/reservationComplete', { state: { reservationId: data } });
    };

    return (
        <div className="border-primary/20 flex items-center gap-4 rounded-2xl border px-4 py-8">
            <div className="bg-primary flex aspect-square items-center rounded">
                <span className="material-symbols-outlined text-white">
                    train
                </span>
            </div>
            <div className="flex-col">
                <h3>{train.trainNickname}</h3>
                <h5>{trainNumber}号</h5>
            </div>
            <div className="flex-col">
                <h1>{departureTime}</h1>
                <h5>東京</h5>
            </div>
            <div className="bg-primary/20 h-1 grow" />
            <div className="flex-col">
                <h1>{arrivalTime}</h1>
                <h5>上野</h5>
            </div>
            <button className="contained_btn" onClick={handleReserveSeat}>
                席を予約する
            </button>
        </div>
    );
}

export default TrainSelectItem;
