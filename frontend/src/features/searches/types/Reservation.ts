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

export type ArgForSend = {
    method: HttpMethod,
    body?: Partial<Reservation>,
    id?: string,
}

export const httpMethod = {
    POST: "POST", 
    PATCH: "PATCH",
    DELETE: "DELETE"
} as const;

export type HttpMethod = typeof httpMethod[keyof typeof httpMethod];

export const isHttpMethod = (value:string): value is HttpMethod => {
    return ["POST", "PATCH", "DELETE"].includes(value);
}
