import {Route} from "@angular/router";

import {StackComponent} from "./stack.component";

export const stackRoute: Route = {
    path: '',
    component: StackComponent,
    outlet: 'stack'
};
