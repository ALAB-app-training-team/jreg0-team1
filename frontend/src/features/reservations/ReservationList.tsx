import { useState } from 'react';

import ReservationSelectItem from '@/features/reservations/ReservationSelectItem';

function ReservationList() {
    const [selectedTab, setSelectedTab] = useState<'active' | 'past'>('active');

    const reservations = [
        {
            id: '00000001',
            reservationDate: '2026-06-20',
            departureDate: '2026-06-20',
            seatLocation: '1A',
            carNumber: 1,
            seatType: '01',
            departureStationName: '東京',
            departureTime: '10:00:00',
            arrivalStationName: '上野',
            arrivalTime: '10:05:00',
            departureTrack: 2,
            trainName: 'hayabusa-1',
            trainNickname: 'はやぶさ',
        },
        {
            id: '00000001',
            reservationDate: '2026-06-20',
            departureDate: '2026-06-24',
            seatLocation: '1A',
            carNumber: 1,
            seatType: '01',
            departureStationName: '東京',
            departureTime: '12:00:00',
            arrivalStationName: '仙台',
            arrivalTime: '13:05:00',
            departureTrack: 4,
            trainName: 'hayabusa-210',
            trainNickname: 'はやぶさ',
        },
        {
            id: '00000001',
            reservationDate: '2026-06-20',
            departureDate: '2026-06-24',
            seatLocation: '1A',
            carNumber: 1,
            seatType: '01',
            departureStationName: '東京',
            departureTime: '12:00:00',
            arrivalStationName: '仙台',
            arrivalTime: '13:05:00',
            departureTrack: 4,
            trainName: 'hayabusa-210',
            trainNickname: 'はやぶさ',
        },
    ];

    //const { data: reservations, error: reservationError } = useSWR<
    //    ReservationDetail[]
    //>(ENDPOINT.RESERVATIONS, fetcher);

    const now = new Date();
    now.setHours(0, 0, 0, 0);

    const activeReservations = reservations?.filter((reservation) => {
        const departureDate = new Date(reservation.departureDate);
        return departureDate >= now;
    });

    const pastReservations = reservations?.filter((reservation) => {
        const departureDate = new Date(reservation.departureDate);
        return departureDate < now;
    });

    const filteredReservations =
        selectedTab === 'active' ? activeReservations : pastReservations;

    return (
        <>
            <div className="mx-auto flex max-w-4xl flex-col gap-8 p-4">
                <h1 className="text-4xl font-bold">予約確認</h1>
                <div className="bg-primary/8 flex gap-6 rounded-3xl p-2">
                    <div className="flex w-full items-center">
                        <button
                            onClick={() => setSelectedTab('active')}
                            className={`flex w-full cursor-pointer items-center justify-center gap-2 rounded-3xl px-6 py-2 transition ${
                                selectedTab === 'active'
                                    ? 'bg-white font-semibold shadow'
                                    : ''
                            } `}
                        >
                            <span className="material-symbols-outlined">
                                calendar_today
                            </span>
                            有効（{activeReservations?.length}）
                        </button>
                        <button
                            onClick={() => setSelectedTab('past')}
                            className={`flex w-full cursor-pointer items-center justify-center gap-2 rounded-3xl px-6 py-2 transition ${
                                selectedTab === 'past'
                                    ? 'bg-white font-semibold shadow'
                                    : ''
                            } `}
                        >
                            <span className="material-symbols-outlined">
                                group
                            </span>
                            過去（{pastReservations?.length}）
                        </button>
                    </div>
                </div>
                {filteredReservations && filteredReservations.length > 0 ? (
                    filteredReservations.map((reservation) => (
                        <ReservationSelectItem
                            key={reservation.id}
                            details={reservation}
                        />
                    ))
                ) : (
                    <></>
                )}
            </div>
        </>
    );
}

export default ReservationList;
