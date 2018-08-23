export class MsEntry {
    constructor(
        public path: string,
        public serviceId: string,
        public serviceInstances: any[],
        public gtwayProperties: string
    ) { }
}
