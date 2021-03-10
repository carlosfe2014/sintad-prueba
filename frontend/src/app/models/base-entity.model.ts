import {Link} from "../interfaces/link.interface";

export class BaseEntity {
    id: number;
    estado: boolean;
    links?: Link[];
}
