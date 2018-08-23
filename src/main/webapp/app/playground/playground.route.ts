import {Route} from "@angular/router";

import {PlaygroundComponent} from "./playground.component";
import {UserRouteAccessService} from "../shared/auth/user-route-access-service";
import {OpCanDeactivateGuard} from "./opCanDeactivateGuard.guard";

export const PLAYGROUND_ROUTE: Route = {
    path: 'playground',
    component: PlaygroundComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'playground Lab'
    },
    canActivate: [UserRouteAccessService],
    canDeactivate: [OpCanDeactivateGuard]
};
