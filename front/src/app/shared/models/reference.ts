export interface IReference {
    code?: string;
    label?: string;
}

export class Reference {
    code!: string;
    label!: string;

    constructor(apiModel?: IReference) {
        if (apiModel) {
            this.code = apiModel.code || '';
            this.label = apiModel.label || '';
        }
    }
}