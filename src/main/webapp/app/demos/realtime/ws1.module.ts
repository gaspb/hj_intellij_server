import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {GtwSharedModule} from "../../shared";

import {DEMO_WS1_ROUTE, Ws1Component} from "./";

@NgModule({
    imports: [
        GtwSharedModule,
        RouterModule.forRoot([ DEMO_WS1_ROUTE ], { useHash: true })
    ],
    declarations: [
        Ws1Component,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GtwWs1Module {}
