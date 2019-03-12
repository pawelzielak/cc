import { IAlias } from 'app/shared/model/alias.model';
import { IRadio } from 'app/shared/model/radio.model';

export interface IFolder {
    id?: number;
    folder?: string;
    note?: string;
    alias?: IAlias;
    radio?: IRadio;
}

export class Folder implements IFolder {
    constructor(public id?: number, public folder?: string, public note?: string, public alias?: IAlias, public radio?: IRadio) {}
}
