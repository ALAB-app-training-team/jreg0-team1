const BASE_URL = import.meta.env.VITE_API_BASE_URL;

const ENDPOINT = {
    TRAINS: (
        departureStationId: string,
        arrivalStationId: string,
        departureDate: string,
    ) =>
        `${BASE_URL}/trains?start=${departureStationId}&end=${arrivalStationId}&date=${departureDate}`,
    RESERVATIONS: `${BASE_URL}/reservations`,
    DETAILS: (reservationId: string) =>
        `${BASE_URL}/reservations/${reservationId}`,
};

export default ENDPOINT;
