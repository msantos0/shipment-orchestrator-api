import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { AppShell } from './components/AppShell'
import { CarrierCreatePage } from './pages/CarrierCreatePage'
import { CarriersPage } from './pages/CarriersPage'

function App() {
  return <BrowserRouter><AppShell><Routes>
    <Route path="/carriers" element={<CarriersPage />} />
    <Route path="/carriers/new" element={<CarrierCreatePage />} />
          <Route path="/carriers/:id/edit" element={<CarrierCreatePage />} />
    <Route path="/" element={<Navigate to="/carriers" replace />} />
    <Route path="*" element={<Navigate to="/carriers" replace />} />
  </Routes></AppShell></BrowserRouter>
}

export default App
