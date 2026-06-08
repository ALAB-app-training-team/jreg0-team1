import type { Train } from "@/features/searches/types/Train";
import usePostReservation from "@/hooks/usePostReservation";
import { httpMethod, type Reservation } from "./types/Reservation";
import { useNavigate } from "react-router-dom";

type TrainSelectItemProps = {
    train: Train,
    boardingStationId: string,
    arrivalStationId: string
};

function TrainSelectItem({
    train,
    boardingStationId,
    arrivalStationId,
}:TrainSelectItemProps){
    const trainNumber: string = train.trainName.split("-")[1];
    const departureTime = train.schedules.find(s => s.stationId === boardingStationId)?.departureTime.slice(0,5);
    const arrivalTime = train.schedules.find(s => s.stationId === arrivalStationId)?.arrivalTime.slice(0,5);
    const {trigger: postTrigger} = usePostReservation();
    const navigate = useNavigate();


    const handleReserveSeat = async() => {
        const reserveToPost: Partial<Reservation> = {
            seatId: "",
            reservationDate: new Date().toISOString().split("T")[0],
            trainId: train.id,
            boardingStationId: boardingStationId,
            destinationStationId: arrivalStationId,
            paymentMethod: "",
            paymentStatus: "",
            fee: 2600,
            accountId: "",
        }
        const data = await postTrigger({method:httpMethod.POST, body:reserveToPost});

        navigate("/reservationComplete", { state: { reservationId: data } });
    }

    return (
    <div className="flex items-center border border-primary/[20%] rounded-2xl py-8 px-4 gap-4">
        <div className="bg-primary rounded aspect-square flex items-center">
            <span className="material-symbols-outlined text-white">train</span>
        </div>
        <div className="flex-col">
            <h3>{train.trainNickName}</h3>
            <h5>{trainNumber}号</h5>
        </div>
        <div className="flex-col">
            <h1>{departureTime}</h1>
            <h5>東京</h5>
        </div>
        <div className="grow h-1 bg-primary/[20%]"></div>
        <div className="flex-col">
            <h1>{arrivalTime}</h1>
            <h5>上野</h5>
        </div>
        <button className="contained_btn"
        onClick={handleReserveSeat}>席を予約する</button>
    </div>
    );
}

export default TrainSelectItem;
