import { useLocation } from "react-router-dom";

function ReservationComplete(){
    const location = useLocation();
    const { state } = location;

    return (
    <div className="flex flex-col items-center">
        <h1>予約が完了しました</h1>
        <h3>予約番号：{state.reservationId}</h3>
    </div>
    );
}

export default ReservationComplete;
