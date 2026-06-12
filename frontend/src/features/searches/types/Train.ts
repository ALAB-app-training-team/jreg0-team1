import type { Schedule } from '@/features/searches/types/Schedule';

export type Train = {
    id: string;
    trainNumber: string;
    trainName: string;
    routeId: string;
    trainNickname: string;
    formation: number;
    schedules: Schedule[];
};
