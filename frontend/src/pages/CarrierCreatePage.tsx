import ArrowBackIcon from '@mui/icons-material/ArrowBack'
import SaveIcon from '@mui/icons-material/Save'
import { Alert, Box, Button, CircularProgress, FormControlLabel, Paper, Stack, Switch, TextField, Typography } from '@mui/material'
import { useEffect, useState, type FormEvent } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import { createCarrier, getCarrier, updateCarrier } from '../services/carrierService'

export function CarrierCreatePage() {
  const navigate = useNavigate()
  const [name, setName] = useState('')
  const [cnpj, setCnpj] = useState('')
  const [active, setActive] = useState(true)
  const [saving, setSaving] = useState(false)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [success, setSuccess] = useState(false)
  const { id } = useParams<{ id: string }>()
  const isEditing = Boolean(id)

  useEffect(() => {
    if (!id) return
    setLoading(true)
    void getCarrier(id).then((carrier) => { setName(carrier.name); setCnpj(carrier.cnpj); setActive(carrier.active) }).catch(() => setError('Could not load carrier details.')).finally(() => setLoading(false))
  }, [id])

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault(); setSaving(true); setError(null)
    try { if (isEditing && id) await updateCarrier(id, { name, active }); else await createCarrier({ name, cnpj }); setSuccess(true); setTimeout(() => navigate('/carriers'), 800) } catch { setError(`Could not ${isEditing ? 'update' : 'create'} carrier. Check the data and try again.`) } finally { setSaving(false) }
  }

  return <>
    <Button component={Link} to="/carriers" startIcon={<ArrowBackIcon />} sx={{ mb: 3, color: '#486581' }}>Back to carriers</Button>
    <Stack spacing={1} sx={{ mb: 4 }}><Typography variant="overline" sx={{ color: '#d97706', fontWeight: 700, letterSpacing: '.14em' }}>NETWORK DIRECTORY</Typography><Typography variant="h3" sx={{ fontFamily: 'Space Grotesk', fontWeight: 700, letterSpacing: '-.05em', color: '#102a43' }}>{isEditing ? 'Edit carrier' : 'Add a carrier'}</Typography><Typography color="text.secondary">{isEditing ? 'Update the carrier profile and active status.' : 'Register a new logistics partner. New carriers start active by default.'}</Typography></Stack>
    <Paper component="form" onSubmit={handleSubmit} elevation={0} sx={{ maxWidth: 680, p: { xs: 2.5, sm: 4 }, border: '1px solid #d9e2ec', borderRadius: 2 }}><Stack spacing={3}>{loading ? <CircularProgress sx={{ color: '#d97706', alignSelf: 'center' }} /> : <><TextField label="Carrier name" value={name} onChange={(event) => setName(event.target.value)} required fullWidth slotProps={{ htmlInput: { maxLength: 120 } }} />{!isEditing && <TextField label="CNPJ" value={cnpj} onChange={(event) => setCnpj(event.target.value)} required fullWidth slotProps={{ htmlInput: { maxLength: 18 } }} />}{isEditing && <FormControlLabel control={<Switch checked={active} onChange={(event) => setActive(event.target.checked)} />} label={active ? 'Active carrier' : 'Inactive carrier'} />}</>}{error && <Alert severity="error">{error}</Alert>}<Box sx={{ display: 'flex', justifyContent: 'flex-end' }}><Button type="submit" variant="contained" disabled={saving || loading} startIcon={<SaveIcon />} sx={{ bgcolor: '#d97706', '&:hover': { bgcolor: '#b45309' } }}>{saving ? 'Saving...' : isEditing ? 'Save changes' : 'Create carrier'}</Button></Box></Stack></Paper>
    {success && <Alert severity="success" sx={{ mt: 2, maxWidth: 680 }}>Carrier {isEditing ? 'updated' : 'created'} successfully.</Alert>}
  </>
}
