import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {GtwSharedModule} from "../shared";

import {IOT_ROUTE, IotComponent} from "./";

@NgModule({
    imports: [
        GtwSharedModule,
        RouterModule.forRoot([ IOT_ROUTE ], { useHash: true })
    ],
    declarations: [
        IotComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GtwIotModule {}
