import {BaseEntity} from "../../../../models/base-entity.model";

export class Entidad extends BaseEntity {
  tipoDocumentoId: number;
  numeroDocumento: string;
  razonSocial: string;
  nombreComercial?: string;
  tipoContribuyenteId?: number;
  direccion?: string;
  telefono?: string;
}
