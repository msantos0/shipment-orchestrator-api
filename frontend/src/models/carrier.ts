export interface Carrier {
  id: string
  name: string
  cnpj: string
  active: boolean
  createdAt: string
}

export interface CarrierPage {
  content: Carrier[]
  page: number
  size: number
  totalElements: number
  totalPages: number
}

export interface CreateCarrierPayload {
  name: string
  cnpj: string
}

export interface UpdateCarrierPayload {
  name: string
  active: boolean
}
