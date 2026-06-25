import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { type Reservation } from '@/features/purchases/types/Reservation';
import SeatSelectModal from '@/features/searches/SeatSelectModal';
import type { Station } from '@/features/searches/types/Station';
import type { Train } from '@/features/searches/types/Train';
import usePostReservation from '@/hooks/usePostReservation';
import { httpMethod } from '@/types/HttpMethod';

type TrainSelectItemProps = {
    train: Train;
    departureStation: Station;
    arrivalStation: Station;
    departureDate: string;
};

function TrainSelectItem({
    train,
    departureStation,
    arrivalStation,
    departureDate,
}: TrainSelectItemProps) {
    const trainNumber: string = train.trainName.split('-')[1];
    const departureTime = train.schedules
        .find((s) => s.stationId === departureStation.id)
        ?.departureTime.slice(0, 5);
    const arrivalTime = train.schedules
        .find((s) => s.stationId === arrivalStation.id)
        ?.arrivalTime.slice(0, 5);
    const { trigger: postTrigger } = usePostReservation();
    const navigate = useNavigate();
    const [ismodalOpen, setIsModalOpen] = useState<boolean>(false);
    const [seatId, setSeatId] = useState<string>('');

    const handleReserveSeat = async () => {
        const reserveToPost: Partial<Reservation> = {
            seatId: seatId,
            reservationDate: new Date().toISOString().split('T')[0],
            departureDate: departureDate,
            trainId: train.id,
            departureStationId: departureStation.id,
            arrivalStationId: arrivalStation.id,
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
                <h5>{departureStation.stationName}</h5>
            </div>
            <div className="bg-primary/20 h-1 grow" />
            <div className="flex-col">
                <h1>{arrivalTime}</h1>
                <h5>{arrivalStation.stationName}</h5>
            </div>
            <button
                className="contained_btn"
                onClick={() => setIsModalOpen(true)}
            >
                席を予約する
            </button>
            {train.id && (
                <SeatSelectModal
                    isOpen={ismodalOpen}
                    setIsOpen={setIsModalOpen}
                    setSeat={setSeatId}
                    trainId={train.id}
                    handleReserveSeat={handleReserveSeat}
                />
            )}
        </div>
    );
}

export default TrainSelectItem;
