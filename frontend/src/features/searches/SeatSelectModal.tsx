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
    const { data: cars, error } = useSWR<Car[]>(
        trainId ? ENDPOINT.CAR_SEATMAP(trainId) : null,
        fetcher,
    );
    console.log(cars);
    console.log(cars?.[0].seats);

    if (error || !cars) {
        return <Error />;
    }

    return (
        <Modal isOpen={isOpen} onRequestClose={() => setIsOpen(false)}>
            <form>
                <h1>座席選択</h1>
                <div className="flex">
                    {cars?.[0].seats.map((seat) => {
                        return (
                            <label>
                                <input
                                    type="radio"
                                    value={seat.id}
                                    onChange={() => setSeat(seat.id)}
                                />
                                {seat.seatLocation.replace(/^\d+/, '')}
                            </label>
                        );
                    })}
                </div>
                <button
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
