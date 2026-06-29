import { useEffect, useState } from 'react';
import Modal from 'react-modal';
import useSWR from 'swr';

import fetcher from '@/api/fetcher';
import Error from '@/components/layout/Error';
import ENDPOINT from '@/constants/Endpoint';
import type { Car } from '@/features/searches/types/Car';

type SeatSelectModalProps = {
    isOpen: boolean;
    setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
    seatId: string;
    setSeat: React.Dispatch<React.SetStateAction<string>>;
    trainId: string;
    handleReserveSeat: () => void;
};

function SeatSelectModal({
    isOpen,
    setIsOpen,
    seatId,
    setSeat,
    trainId,
    handleReserveSeat,
}: SeatSelectModalProps) {
    const [selectedCar, setSelectedCar] = useState<string>('1');
    const { data: cars, error } = useSWR<Car[]>(
        trainId ? ENDPOINT.CAR_SEATMAP(trainId) : null,
        fetcher,
    );

    useEffect(() => {
        if (cars) {
            setSelectedCar(cars[0].id);
        }
    }, [cars]);

    if (error || !cars) {
        return <Error />;
    }

    const handleCarSelect = (carId: string) => {
        setSelectedCar(carId);
    };

    const handleSeatSelect = (seatId: string) => {
        setSeat(seatId);
    };

    const car = cars.find((car) => car.id === selectedCar);

    return (
        <Modal
            isOpen={isOpen}
            onRequestClose={() => setIsOpen(false)}
            className={
                'mx-auto mt-10 max-w-[700px] rounded-lg border-1 border-gray-300 bg-white p-6'
            }
        >
            <h1>座席選択</h1>
            <div>
                <h5>号車を選択</h5>
                <div className="flex flex-wrap p-2">
                    {cars?.map((car) => {
                        return (
                            <button
                                key={car.id}
                                onClick={() => handleCarSelect(car.id)}
                                className={`h-20 w-20 cursor-pointer rounded-lg border-2 font-semibold transition ${selectedCar === car.id ? 'border-primary bg-primary/10' : 'border-gray'}`}
                            >
                                <p
                                    className={`${selectedCar === car.id ? 'text-primary' : 'text-black'}`}
                                >
                                    {car.carNumber}
                                </p>
                                <p className="text-gray-500">
                                    {car.seats.length}席
                                </p>
                            </button>
                        );
                    })}
                </div>
            </div>
            <h3 className="py-2 font-semibold">{car?.carNumber}号車</h3>
            <div className="flex gap-4 py-4">
                {car?.seats.map((seat) => {
                    return (
                        <button
                            key={seat.id}
                            onClick={() => handleSeatSelect(seat.id)}
                            className={`h-14 w-14 cursor-pointer rounded-lg border transition ${seat?.id === seatId ? 'border-primary bg-primary/10' : 'border-gray'}`}
                        >
                            {seat.seatLocation.replace(/^\d+/, '')}
                        </button>
                    );
                })}
            </div>
            <div className="flex cursor-pointer justify-end gap-4 pt-6">
                <button
                    className="outlined-btn cursor-pointer"
                    onClick={() => setIsOpen(false)}
                >
                    キャンセル
                </button>
                <button
                    className="contained_btn contained_btndisabled:cursor-not-allowed cursor-pointer"
                    disabled={!seatId}
                    onClick={() => {
                        setIsOpen(false);
                        handleReserveSeat();
                    }}
                >
                    予約確定
                </button>
            </div>
        </Modal>
    );
}

export default SeatSelectModal;
