import React from "react";
import ReactDOM from "react-dom";
import "98.css";

import HrServiceWindow from "./components/HrServiceWindow";




function App() {
    return (
        <div className="App">
            <HrServiceWindow />
        </div>
    );
}
const rootElement = document.getElementById("root");
ReactDOM.render(
    <React.StrictMode>
      <div
          style={{
            height: "100vh",
            display: "flex",
            justifyContent: "center",
            alignItems: "center"
          }}
      >
        <div style={{ width: 350 }}>
          <App />
        </div>
      </div>
    </React.StrictMode>,
    rootElement
);

export default App;
