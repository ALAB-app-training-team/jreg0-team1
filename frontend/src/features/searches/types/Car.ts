import type { Seat } from '@/features/searches/types/Seat';

export type Car = {
    carNumber: number;
    id: string;
    seatType: string;
    seats: Seat[];
    trainId: string;
};
