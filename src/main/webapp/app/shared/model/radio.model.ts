import { IFolder } from 'app/shared/model/folder.model';
import { ICCG } from 'app/shared/model/ccg.model';
import { IAlias } from 'app/shared/model/alias.model';

export interface IRadio {
    id?: number;
    folder?: string;
    alias?: string;
    ccg?: string;
    note?: string;
    folders?: IFolder[];
    folders?: ICCG[];
    aliases?: IAlias[];
}

export class Radio implements IRadio {
    constructor(
        public id?: number,
        public folder?: string,
        public alias?: string,
        public ccg?: string,
        public note?: string,
        public folders?: IFolder[],
        public folders?: ICCG[],
        public aliases?: IAlias[]
    ) {}
}
