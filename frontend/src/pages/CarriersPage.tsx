import RefreshIcon from '@mui/icons-material/Refresh'
import AddIcon from '@mui/icons-material/Add'
import { Alert, Box, Button, CircularProgress, Stack, Typography } from '@mui/material'
import { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import type { Carrier } from '../models/carrier'
import { CarrierTable } from '../components/CarrierTable'
import { deleteCarrier, listCarriers } from '../services/carrierService'

export function CarriersPage() {
  const [carriers, setCarriers] = useState<Carrier[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [success, setSuccess] = useState<string | null>(null)
  const navigate = useNavigate()

  async function loadCarriers() {
    setLoading(true)
    setError(null)
    try { setCarriers((await listCarriers()).content) } catch { setError('Could not load carriers. Check that the API is running.') } finally { setLoading(false) }
  }

  useEffect(() => { void loadCarriers() }, [])

  async function handleDelete(carrier: Carrier) {
    if (!window.confirm(`Delete ${carrier.name}? This will deactivate the carrier.`)) return
    setError(null)
    try { await deleteCarrier(carrier.id); setCarriers((current) => current.filter((item) => item.id !== carrier.id)); setSuccess('Carrier deactivated successfully.') } catch { setError('Could not delete carrier. Please try again.') }
  }

  return <>
    <Stack spacing={1} sx={{ mb: 4 }}><Typography variant="overline" sx={{ color: '#d97706', fontWeight: 700, letterSpacing: '.14em' }}>NETWORK DIRECTORY</Typography><Typography variant="h3" sx={{ fontFamily: 'Space Grotesk', fontWeight: 700, letterSpacing: '-.05em', color: '#102a43' }}>Carriers</Typography><Typography color="text.secondary">Manage the logistics partners connected to your shipment network.</Typography></Stack>
    <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} sx={{ mb: 2, justifyContent: 'space-between', alignItems: { sm: 'center' } }}><Typography variant="body2" color="text.secondary">{loading ? 'Loading directory...' : `${carriers.length} carrier${carriers.length === 1 ? '' : 's'} in directory`}</Typography><Stack direction="row" spacing={1}><Button startIcon={<RefreshIcon />} onClick={() => void loadCarriers()} variant="text">Refresh</Button><Button component={Link} to="/carriers/new" startIcon={<AddIcon />} variant="contained" sx={{ bgcolor: '#d97706', '&:hover': { bgcolor: '#b45309' } }}>New carrier</Button></Stack></Stack>
    {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
    {loading ? <Box sx={{ display: 'grid', placeItems: 'center', minHeight: 260 }}><CircularProgress sx={{ color: '#d97706' }} /></Box> : carriers.length ? <CarrierTable carriers={carriers} onEdit={(id) => navigate(`/carriers/${id}/edit`)} onDelete={(carrier) => void handleDelete(carrier)} /> : <Alert severity="info">No carriers found in the directory.</Alert>}
    {success && <Alert severity="success" onClose={() => setSuccess(null)} sx={{ mt: 2 }}>{success}</Alert>}
  </>
}
