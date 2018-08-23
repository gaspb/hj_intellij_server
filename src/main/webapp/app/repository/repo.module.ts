import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {GtwSharedModule} from "../shared";

import {REPO_ROUTE, RepoComponent} from "./";

@NgModule({
    imports: [
        GtwSharedModule,
        RouterModule.forRoot([ REPO_ROUTE ], { useHash: true }),
    ],
    declarations: [
        RepoComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GtwRepoModule {}
