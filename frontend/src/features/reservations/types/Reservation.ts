import type { HttpMethod } from "@/types/HttpMethod";

export type Reservation = {
    id: string,
    seatId: string,
    reservationDate: string,
    trainId: string,
    boardingStationId: string,
    destinationStationId: string,
    paymentMethod: string,
    paymentStatus: string,
    fee: number,
    accountId: string,
};

export type ReservationForSend = {
    method: HttpMethod,
    body?: Partial<Reservation>,
    id?: string,
}
