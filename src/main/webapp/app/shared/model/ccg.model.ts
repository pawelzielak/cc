import { IRadio } from 'app/shared/model/radio.model';

export interface ICCG {
    id?: number;
    ccg?: string;
    note?: string;
    radio?: IRadio;
}

export class CCG implements ICCG {
    constructor(public id?: number, public ccg?: string, public note?: string, public radio?: IRadio) {}
}
