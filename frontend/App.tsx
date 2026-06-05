import { BrowserRouter, Route, Routes } from 'react-router-dom'
import 'material-symbols';
import './App.css'
import TrainSearchResult from '@/features/searches/TrainSearchResult'

function App() {
  return (
    <>
      <BrowserRouter>
      <Routes>
        <Route path="/" element={<TrainSearchResult/>} />
      </Routes>
      </BrowserRouter>
    </>
  )
};

export default App;
