import 'material-symbols';
import '@/App.css';

import { BrowserRouter, Route, Routes } from 'react-router-dom';

import MainLayout from '@/components/layout/MainLayout';
import DeleteAllReservation from '@/features/manages/DeleteAllReservation';
import ReservationComplete from '@/features/purchases/ReservationComplete';
import ReservationInfo from '@/features/reservations/ReservationInfo';
import TrainSearchResult from '@/features/searches/TrainSearchResult';

function App() {
    return (
        <>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<MainLayout />}>
                        <Route index element={<TrainSearchResult />} />
                        <Route
                            path="/reservationComplete"
                            element={<ReservationComplete />}
                        />
                        <Route
                            path="/details/:id"
                            element={<ReservationInfo />}
                        />
                        <Route
                            path="/delete-all-reservation"
                            element={<DeleteAllReservation />}
                        />
                    </Route>
                </Routes>
            </BrowserRouter>
        </>
    );
}

export default App;
