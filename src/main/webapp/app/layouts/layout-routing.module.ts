import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";

import {navbarRoute} from "../app.route";
import {errorRoute} from "./";
import {stackRoute} from "./stack/stack.route";

const LAYOUT_ROUTES = [
    stackRoute,
    navbarRoute,
    ...errorRoute
];

@NgModule({
    imports: [
        RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true })
    ],
    exports: [
        RouterModule
    ]
})
export class LayoutRoutingModule {}
