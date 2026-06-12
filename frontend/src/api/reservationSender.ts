import type { ReservationForSend } from '@/features/reservations/types/Reservation';

const reservationSender = (
    url: string,
    { arg }: { arg: ReservationForSend },
): Promise<string> => {
    const data = fetch(url, {
        method: arg.method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: arg.body ? JSON.stringify(arg.body) : undefined,
    }).then((res) => res.text());
    return data;
};

export default reservationSender;
