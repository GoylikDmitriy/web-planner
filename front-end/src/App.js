import {BrowserRouter, Routes, Route} from "react-router-dom";
import Planner from "./component/Planner"

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
            <Route path={'/'} element={<Planner/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
