import { api } from './api'
import type { Carrier, CarrierPage, CreateCarrierPayload } from '../models/carrier'

export async function listCarriers(): Promise<CarrierPage> {
  const { data } = await api.get<CarrierPage>('/carriers', { params: { page: 0, size: 100 } })
  return data
}

export async function createCarrier(payload: CreateCarrierPayload): Promise<Carrier> {
  const { data } = await api.post<Carrier>('/carriers', payload)
  return data
}
