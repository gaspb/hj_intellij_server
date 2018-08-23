import {Route} from "@angular/router";

import {DocumentationComponent} from "./documentation.component";

export const DOCUMENTATION_ROUTE: Route = {
    path: 'documentation',
    component: DocumentationComponent,
    data: {
        authorities: [],
        pageTitle: 'Documentation'
    }
};
