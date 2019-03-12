import { IFolder } from 'app/shared/model/folder.model';
import { IRadio } from 'app/shared/model/radio.model';

export interface IAlias {
    id?: number;
    alias?: string;
    folder?: string;
    note?: string;
    folders?: IFolder[];
    radio?: IRadio;
}

export class Alias implements IAlias {
    constructor(
        public id?: number,
        public alias?: string,
        public folder?: string,
        public note?: string,
        public folders?: IFolder[],
        public radio?: IRadio
    ) {}
}
