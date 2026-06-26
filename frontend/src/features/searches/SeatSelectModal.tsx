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
    setSeat: React.Dispatch<React.SetStateAction<string>>;
    trainId: string;
    handleReserveSeat: () => void;
};

function SeatSelectModal({
    isOpen,
    setIsOpen,
    setSeat,
    trainId,
    handleReserveSeat,
}: SeatSelectModalProps) {
    const [selectedCar, setSelectedCar] = useState<string | undefined>(
        undefined,
    );
    const { data: cars, error } = useSWR<Car[]>(
        trainId ? ENDPOINT.CAR_SEATMAP(trainId) : null,
        fetcher,
    );

    if (error || !cars) {
        return <Error />;
    }

    useEffect(() => {
        if (cars) {
            setSelectedCar(cars[0].id);
        }
    }, [cars]);

    const handleCarSelect = (carId: string) => {
        setSelectedCar(carId);
    };

    return (
        <Modal isOpen={isOpen} onRequestClose={() => setIsOpen(false)}>
            <form>
                <h1>座席選択</h1>
                <div>
                    <h5>号車を選択</h5>
                    <div>
                        {cars?.map((car) => {
                            return (
                                <label className="checked:border-primary flex aspect-square w-8 flex-col items-center justify-center rounded-lg border border-gray-700">
                                    <input
                                        type="radio"
                                        className="hidden"
                                        value={car.id}
                                        onChange={() => handleCarSelect(car.id)}
                                    />
                                    <p>{car.carNumber}</p>
                                    <p>{car.seats.length}席</p>
                                </label>
                            );
                        })}
                    </div>
                </div>
                <div className="flex gap-4">
                    {cars
                        ?.find((c) => c.id == selectedCar)
                        .seats.map((seat) => {
                            return (
                                <label className="checked:border-primary flex aspect-square w-8 items-center justify-center rounded-lg border border-gray-700">
                                    <input
                                        type="radio"
                                        className="hidden"
                                        value={seat.id}
                                        onChange={() => setSeat(seat.id)}
                                    />
                                    {seat.seatLocation.replace(/^\d+/, '')}
                                </label>
                            );
                        })}
                </div>
                <button
                    className="contained_btn"
                    onClick={() => {
                        setIsOpen(false);
                        handleReserveSeat();
                    }}
                >
                    予約確定
                </button>
            </form>
        </Modal>
    );
}

export default SeatSelectModal;
