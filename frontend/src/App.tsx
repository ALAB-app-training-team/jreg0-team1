import "material-symbols";
import "@/App.css";

import { BrowserRouter, Route, Routes } from "react-router-dom";

import ReservationInfo from "@/features/details/ReservationInfo";
import ReservationComplete from "@/features/reservations/ReservationComplete";
import TrainSearchResult from "@/features/searches/TrainSearchResult";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<TrainSearchResult />} />
          <Route
            path="/reservationComplete"
            element={<ReservationComplete />}
          />
          <Route path="/details/:id" element={<ReservationInfo />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
