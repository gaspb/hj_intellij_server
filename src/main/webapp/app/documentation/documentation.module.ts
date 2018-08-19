import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {GtwSharedModule} from "../shared";

import {DOCUMENTATION_ROUTE, DocumentationComponent} from "./";

@NgModule({
    imports: [
        GtwSharedModule,
        RouterModule.forRoot([ DOCUMENTATION_ROUTE ], { useHash: true })
    ],
    declarations: [
        DocumentationComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GtwDocumentationModule {}
