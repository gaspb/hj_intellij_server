import {Component, OnInit} from "@angular/core";
import {StackService} from "../../shared/stack/stack.service";
import {NavigationStart, Router} from "@angular/router";
import {Routes} from "../../shared/stack/routes.temporary-constant";

@Component({
    selector: 'stack',
    templateUrl: './stack.component.html'
})
export class StackComponent implements OnInit{
    stackToggled;
    fullstack;



    constructor(
        private stackService : StackService,
        private router: Router
    ) {
    }
    ngOnInit() {
        this.stackToggled = false;
/*        this.stackService.connect().then(stack => this.fullstack = );*/
        console.log("debug11 "+this.fullstack);
        this.router.events.subscribe(event => {
            if(event instanceof NavigationStart) {
                console.log("<<<<<<<<<<<<<<<debug8 - ", event.url);
                this.fullstack = Routes[event.url];
            }
        });
    }

}
