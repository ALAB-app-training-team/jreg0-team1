import reservationSender from '@/api/reservationSender';
import ENDPOINT from '@/constants/Endpoint';
import type { ReservationForSend } from '@/features/reservations/types/Reservation';
import useSWRMutation from 'swr/mutation'

const usePostReservation = () => {
    return useSWRMutation<String, Error, string, ReservationForSend>(ENDPOINT.RESERVATIONS, reservationSender)
}

export default usePostReservation;
