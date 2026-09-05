import ArrowBackIcon from '@mui/icons-material/ArrowBack'
import SaveIcon from '@mui/icons-material/Save'
import { Alert, Box, Button, Paper, Stack, TextField, Typography } from '@mui/material'
import { useState, type FormEvent } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { createCarrier } from '../services/carrierService'

export function CarrierCreatePage() {
  const navigate = useNavigate()
  const [name, setName] = useState('')
  const [cnpj, setCnpj] = useState('')
  const [saving, setSaving] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [success, setSuccess] = useState(false)

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault(); setSaving(true); setError(null)
    try { await createCarrier({ name, cnpj }); setSuccess(true); setTimeout(() => navigate('/carriers'), 800) } catch { setError('Could not create carrier. Check the data and try again.') } finally { setSaving(false) }
  }

  return <>
    <Button component={Link} to="/carriers" startIcon={<ArrowBackIcon />} sx={{ mb: 3, color: '#486581' }}>Back to carriers</Button>
    <Stack spacing={1} sx={{ mb: 4 }}><Typography variant="overline" sx={{ color: '#d97706', fontWeight: 700, letterSpacing: '.14em' }}>NETWORK DIRECTORY</Typography><Typography variant="h3" sx={{ fontFamily: 'Space Grotesk', fontWeight: 700, letterSpacing: '-.05em', color: '#102a43' }}>Add a carrier</Typography><Typography color="text.secondary">Register a new logistics partner. New carriers start active by default.</Typography></Stack>
    <Paper component="form" onSubmit={handleSubmit} elevation={0} sx={{ maxWidth: 680, p: { xs: 2.5, sm: 4 }, border: '1px solid #d9e2ec', borderRadius: 2 }}><Stack spacing={3}><TextField label="Carrier name" value={name} onChange={(event) => setName(event.target.value)} required fullWidth slotProps={{ htmlInput: { maxLength: 120 } }} /><TextField label="CNPJ" value={cnpj} onChange={(event) => setCnpj(event.target.value)} required fullWidth slotProps={{ htmlInput: { maxLength: 18 } }} helperText="Use the 14-digit registration number." />{error && <Alert severity="error">{error}</Alert>}<Box sx={{ display: 'flex', justifyContent: 'flex-end' }}><Button type="submit" variant="contained" disabled={saving} startIcon={<SaveIcon />} sx={{ bgcolor: '#d97706', '&:hover': { bgcolor: '#b45309' } }}>{saving ? 'Saving...' : 'Create carrier'}</Button></Box></Stack></Paper>
    {success && <Alert severity="success" sx={{ mt: 2, maxWidth: 680 }}>Carrier created successfully.</Alert>}
  </>
}
