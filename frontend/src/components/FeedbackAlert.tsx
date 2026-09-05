import { Alert, Snackbar } from '@mui/material'

export function FeedbackAlert({ message, onClose, severity = 'success' }: { message: string | null; onClose: () => void; severity?: 'success' | 'error' }) {
  return <Snackbar open={Boolean(message)} autoHideDuration={5000} onClose={onClose} anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}><Alert onClose={onClose} severity={severity} variant="filled" sx={{ width: '100%' }}>{message}</Alert></Snackbar>
}
