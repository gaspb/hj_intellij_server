import {Route} from "@angular/router";
import {MSGatewayRootComponent} from "./msgatewayroot.component";

export const MSTEMPLATE_ROUTE: Route = {
    path: 'lab',
    component: MSGatewayRootComponent,
    data: {
        authorities: [],
        pageTitle: 'Render MS Templates'
    }
};
