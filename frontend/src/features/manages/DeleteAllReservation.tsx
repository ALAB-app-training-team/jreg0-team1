import ENDPOINT from "@/constants/Endpoint"
import Error from "@/components/layout/Error";
import { useNavigate } from "react-router-dom";
import { useState } from "react";


function DeleteAllReservation() {
    const navigate = useNavigate();
    const [status, setStatus] = useState("pendding");

    const handleDeleteAll = async() => {
        try {
            const response = await fetch(ENDPOINT.RESERVATIONS,{
                method: "DELETE"
            });
            setStatus(response.ok ? "done" : "error")
        } catch {
            return <Error />
        }   
    }

    if (status === "done"){
        return (
            <div className="flex flex-col items-center gap-8">
                <h1>削除しました</h1>
                <button className="contained_btn" onClick={()=> navigate("/")}>
                    検索画面に戻る
                </button>
            </div>
        )
    } else if(status === "error") {
        return <Error />
    }

    return (
        <button className="flex contained_btn place-self-center bg-logout hover:bg-logout/50" onClick={()=>handleDeleteAll()}>
            予約を全削除
        </button>
    )
}

export default DeleteAllReservation
