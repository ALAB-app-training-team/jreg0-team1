import './App.css'
import TrainSearchList from './src/features/searches/TrainSearchResult'
import 'material-symbols';
import { BrowserRouter, Route, Routes } from 'react-router-dom'

function App() {
  return (
    <>
      <BrowserRouter>
      <Routes>
        <Route path="/" element={<TrainSearchList/>} />
      </Routes>
      </BrowserRouter>
    </>
  )
};

export default App;
