import './App.css'
import TrainSearchResult from './src/features/searches/TrainSearchResult'
import 'material-symbols';
import { BrowserRouter, Route, Routes } from 'react-router-dom'

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
