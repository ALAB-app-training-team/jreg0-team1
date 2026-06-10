import { BrowserRouter, Route, Routes } from "react-router-dom";
import "material-symbols";
import "./App.css";
import TrainSearchResult from "@/features/searches/TrainSearchResult";
import ReservationComplete from "@/features/reservations/ReservationComplete";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<TrainSearchResult />} />
          <Route path="/reservationComplete" element={<ReservationComplete />} />
          <Route path="/details/:id" element={<ReservationDetail />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
