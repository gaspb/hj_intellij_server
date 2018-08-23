import {ApiDoc} from "./apidoc.model";
export class MSDTO {


    constructor(
        public path: string,
        public serviceId: string,
        public serviceInstances: any[],
        public gtwayProperties: string,
        public apiDoc: ApiDoc[]
    ) {


       apiDoc.forEach(api => api.msDTO = this);
    }
}
