import 'material-symbols';
import '@/App.css';

import { BrowserRouter, Route, Routes } from 'react-router-dom';

import ReservationInfo from '@/features/details/ReservationInfo';
import DeleteAllReservation from '@/features/manages/DeleteAllReservation';
import ReservationComplete from '@/features/reservations/ReservationComplete';
import TrainSearchResult from '@/features/searches/TrainSearchResult';

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
                    <Route
                        path="/delete-all-reservation"
                        element={<DeleteAllReservation />}
                    />
                </Routes>
            </BrowserRouter>
        </>
    );
}

export default App;
