import {Route} from "@angular/router";

import {Ws1Component} from "./ws1.component";

export const DEMO_WS1_ROUTE: Route = {
    path: 'demo-ws1',
    component: Ws1Component,
    data: {
        authorities: [],
        pageTitle: 'Realtime Lab'
    }
};
