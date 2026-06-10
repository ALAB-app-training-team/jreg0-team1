import { useNavigate, useLocation } from "react-router-dom";

function ReservationComplete() {
  const location = useLocation();
  const { state } = location;
  const navigate = useNavigate();

  return (
    <div className="flex flex-col items-center">
      <h1>予約が完了しました</h1>
      <h3>予約番号：{state.reservationId}</h3>
      <button
        className="contained_btn"
        onClick={() => navigate("/reservation/${state.reservationId}")}
      >
        予約情報詳細
      </button>
    </div>
  );
}

export default ReservationComplete;
