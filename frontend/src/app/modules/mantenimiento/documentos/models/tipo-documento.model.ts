import {BaseEntity} from "../../../../models/base-entity.model";

export class TipoDocumento extends BaseEntity{
    codigo: string;
    nombre: string;
    descripcion?: string;
}
