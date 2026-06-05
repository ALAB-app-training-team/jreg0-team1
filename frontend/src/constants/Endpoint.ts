const BASE_URL = import .meta.env.API_BASE_URL;

const ENDPOINT = {
    TRAINS: (boardingStationId: string, destinationStationId: string, departureDate: string) => `${BASE_URL}/trains?start=${boardingStationId}&end=${destinationStationId}&date=${departureDate}`,
    STATIONS: () => `${BASE_URL}/stations`,
}

export default ENDPOINT;
