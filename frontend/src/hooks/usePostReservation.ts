import reservationSender from '@/api/reservationSender';
import ENDPOINT from '@/constants/Endpoint';
import type { ArgForSend, Reservation } from '@/features/searches/types/Reservation';
import useSWRMutation from 'swr/mutation'

const usePostReservation = () => {
    return useSWRMutation<String, Error, string, ArgForSend>(ENDPOINT.RESERVATIONS, reservationSender)
}

export default usePostReservation;
