const BASE_URL = import .meta.env.VITE_API_BASE_URL;

const ENDPOINT = {
    TRAINS: (boardingStationId: string, destinationStationId: string, departureDate: string) => `${BASE_URL}/trains?start=${boardingStationId}&end=${destinationStationId}&date=${departureDate}`,
    RESERVATIONS: `${BASE_URL}/reservations`,
}

export default ENDPOINT;
