import { useLocation, useNavigate } from 'react-router-dom';

function ReservationComplete() {
    const location = useLocation();
    const { state } = location;
    const navigate = useNavigate();

    return (
        <div className="mx-auto flex max-w-4xl flex-col gap-4">
            <button
                className="mt-4 flex cursor-pointer gap-4 p-2 hover:text-black/50"
                onClick={() => navigate('/')}
            >
                <span className="material-symbols-outlined">arrow_back</span>
                検索画面に戻る
            </button>
            <div className="flex flex-col items-center gap-8">
                <div className="flex flex-col text-center">
                    <h1>予約が完了しました</h1>
                    <h3>予約番号：{state.reservationId}</h3>
                </div>
                <button
                    className="contained_btn cursor-pointer"
                    onClick={() => navigate(`/details/${state.reservationId}`)}
                >
                    予約情報詳細
                </button>
            </div>
        </div>
    );
}

export default ReservationComplete;
