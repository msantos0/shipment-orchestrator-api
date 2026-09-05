import { api } from './api'
import type { Carrier, CarrierPage, CreateCarrierPayload, UpdateCarrierPayload } from '../models/carrier'

export async function listCarriers(): Promise<CarrierPage> {
  const { data } = await api.get<CarrierPage>('/carriers', { params: { page: 0, size: 100 } })
  if (!data || !Array.isArray(data.content)) throw new Error('Invalid carriers response')
  return data
}

export async function createCarrier(payload: CreateCarrierPayload): Promise<Carrier> {
  const { data } = await api.post<Carrier>('/carriers', payload)
  return data
}

export async function getCarrier(id: string): Promise<Carrier> {
  const { data } = await api.get<Carrier>(`/carriers/${id}`)
  return data
}

export async function updateCarrier(id: string, payload: UpdateCarrierPayload): Promise<Carrier> {
  const { data } = await api.put<Carrier>(`/carriers/${id}`, payload)
  return data
}

export async function deleteCarrier(id: string): Promise<void> {
  await api.delete(`/carriers/${id}`)
}
