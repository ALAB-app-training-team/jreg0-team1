import useSWRMutation from 'swr/mutation';

import reservationSender from '@/api/reservationSender';
import ENDPOINT from '@/constants/Endpoint';
import type { ReservationForSend } from '@/features/reservations/types/Reservation';

const usePostReservation = () => {
    return useSWRMutation<string, Error, string, ReservationForSend>(
        ENDPOINT.RESERVATIONS,
        reservationSender,
    );
};

export default usePostReservation;
