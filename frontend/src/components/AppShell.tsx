import AddBusinessOutlinedIcon from '@mui/icons-material/AddBusinessOutlined'
import HubOutlinedIcon from '@mui/icons-material/HubOutlined'
import LocalShippingOutlinedIcon from '@mui/icons-material/LocalShippingOutlined'
import MenuIcon from '@mui/icons-material/Menu'
import { Box, Container, Drawer, IconButton, List, ListItemButton, ListItemIcon, ListItemText, Toolbar, Typography, useMediaQuery } from '@mui/material'
import type { ReactNode } from 'react'
import { NavLink } from 'react-router-dom'
import { useState } from 'react'

const drawerWidth = 248

export function AppShell({ children }: { children: ReactNode }) {
  const isMobile = useMediaQuery('(max-width:600px)')
  const [mobileOpen, setMobileOpen] = useState(false)
  const navigation = <>
    <Toolbar sx={{ px: 3, minHeight: '88px !important' }}>
      <Box sx={{ width: 40, height: 40, display: 'grid', placeItems: 'center', background: '#f6ad55', color: '#102a43', borderRadius: 2 }}><HubOutlinedIcon /></Box>
      <Box sx={{ ml: 1.5 }}><Typography sx={{ fontFamily: 'Space Grotesk', fontWeight: 700, color: '#fff' }}>SHIPLINE</Typography><Typography variant="caption" sx={{ color: '#829ab1' }}>OPERATIONS CONSOLE</Typography></Box>
    </Toolbar>
    <Box sx={{ px: 1.5, mt: 3 }}><Typography variant="overline" sx={{ px: 2, color: '#829ab1', letterSpacing: '.12em' }}>Workspace</Typography><List sx={{ mt: 1 }}>
      <ListItemButton component={NavLink} to="/carriers" onClick={() => setMobileOpen(false)} sx={{ borderRadius: 2, mb: .5, '&.active': { background: '#1f4868', color: '#fff' }, '&:hover': { background: '#183c5a' } }}><ListItemIcon sx={{ color: 'inherit', minWidth: 38 }}><LocalShippingOutlinedIcon /></ListItemIcon><ListItemText primary="Carriers" /></ListItemButton>
      <ListItemButton component={NavLink} to="/carriers/new" onClick={() => setMobileOpen(false)} sx={{ borderRadius: 2, '&.active': { background: '#1f4868', color: '#fff' }, '&:hover': { background: '#183c5a' } }}><ListItemIcon sx={{ color: 'inherit', minWidth: 38 }}><AddBusinessOutlinedIcon /></ListItemIcon><ListItemText primary="New carrier" /></ListItemButton>
    </List></Box>
    <Box sx={{ mt: 'auto', p: 3, borderTop: '1px solid #1f4868' }}><Typography variant="caption" sx={{ color: '#829ab1' }}>API STATUS</Typography><Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mt: .8 }}><Box sx={{ width: 8, height: 8, borderRadius: '50%', bgcolor: '#68d391' }} /><Typography variant="body2">Connected to local API</Typography></Box></Box>
  </>
  return (
    <Box sx={{ display: 'flex', minHeight: '100vh', background: '#f3f7fa' }}>
      <Drawer variant={isMobile ? 'temporary' : 'permanent'} open={isMobile ? mobileOpen : true} onClose={() => setMobileOpen(false)} sx={{ width: drawerWidth, flexShrink: 0, '& .MuiDrawer-paper': { width: drawerWidth, border: 0, background: '#102a43', color: '#d9e2ec' } }}>{navigation}</Drawer>
      <Box component="main" sx={{ flexGrow: 1, minWidth: 0 }}><Container maxWidth="xl" sx={{ py: { xs: 2, md: 6 }, px: { xs: 2, md: 6 } }}>{isMobile && <IconButton onClick={() => setMobileOpen(true)} aria-label="Open navigation" sx={{ mb: 1, color: '#102a43' }}><MenuIcon /></IconButton>}{children}</Container></Box>
    </Box>
  )
}
