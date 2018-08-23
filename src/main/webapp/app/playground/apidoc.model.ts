import {MSDTO} from "./msdto.model";
export class ApiDoc {

    //parent, set when instanciating the MSDTO
    msDTO : MSDTO;


    constructor(
        public path: string,
        public reqType : string,
        public serviceId: string,
        public serviceInstances: any[],
        public requestHeader: Object,
        public responseHeader: string,
        public inputDataType: string,
        public outputDataType: string,
    ) { }
}
