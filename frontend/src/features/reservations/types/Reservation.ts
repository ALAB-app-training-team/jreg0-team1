import type { HttpMethod } from '@/types/HttpMethod';

export type Reservation = {
    id: string;
    seatId: string;
    reservationDate: string;
    departureDate: string;
    trainId: string;
    departureStationId: string;
    arrivalStationId: string;
    paymentMethod: string;
    paymentStatus: string;
    fee: number;
    accountId: string;
};

export type ReservationForSend = {
    method: HttpMethod;
    body?: Partial<Reservation>;
    id?: string;
};
