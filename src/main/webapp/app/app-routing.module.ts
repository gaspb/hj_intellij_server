import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {errorRoute, navbarRoute} from "./layouts";
import {DEBUG_INFO_ENABLED} from "./app.constants";
import {stackRoute} from "./layouts/stack/stack.route";

const LAYOUT_ROUTES = [
    stackRoute,
    navbarRoute,
    ...errorRoute
];

@NgModule({
    imports: [
        RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true , enableTracing: DEBUG_INFO_ENABLED })
    ],
    exports: [
        RouterModule
    ]
})
export class GtwAppRoutingModule {}
