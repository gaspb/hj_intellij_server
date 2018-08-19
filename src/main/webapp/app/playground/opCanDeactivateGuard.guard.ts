/**
 * Created by High Jack on 16/06/2018.
 */
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot} from "@angular/router";
import {OperationComponent} from "./operationview.component";


@Injectable()
export class OpCanDeactivateGuard implements CanDeactivate<OperationComponent> {

    canDeactivate(
        component: OperationComponent,
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | boolean {
        // you can just return true or false synchronously
/*        if (expr === true) {
            return true;
        }*/
        // or, you can also handle the guard asynchronously, e.g.
        // asking the user for confirmation.
        return confirm('Unsaved data will be lost. Discard changes?');
    }
}
