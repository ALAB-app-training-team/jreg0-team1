import { BrowserRouter, Route, Routes } from "react-router-dom";
import "material-symbols";
import "./App.css";
import TrainSearchResult from "@/features/searches/TrainSearchResult";
import ReservationComplete from "@/features/reservations/ReservationComplete";
import ReservationInfo from "@/features/details/ReservationInfo";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<TrainSearchResult />} />
          <Route path="/reservationComplete" element={<ReservationComplete />} />
          <Route path="/details/:id" element={<ReservationInfo />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
