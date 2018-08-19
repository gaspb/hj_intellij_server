import {Route} from "@angular/router";
import {RepoComponent} from "./repo.component";

export const REPO_ROUTE: Route = {
    path: 'repository',
    component: RepoComponent,
    data: {
        authorities: [],
        pageTitle: 'Repository'
    }
};
