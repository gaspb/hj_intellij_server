import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager} from "ng-jhipster";

import {Account, LoginModalService, Principal} from "../../shared";
import {Ws1MessageService} from "../../shared/tracker/ws1-message.service";
import {CSRFService} from "../../shared/auth/csrf.service";
import {CookieService} from "ngx-cookie";

@Component({
    selector: 'demo-ws1',
    templateUrl: './ws1.component.html',
    styleUrls: [
        'ws1.scss'
    ],
    host: {
        class:'fullpage-router'
    }


})
export class Ws1Component implements OnInit {
    @ViewChild('wscont') private myScrollContainer: ElementRef;
    account: Account;
    modalRef: NgbModalRef;
    activities: any[] = [];
    toggled;
    isToggledInfo = false;
    isAnon = true;
    constructor(
      private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private ws1MessageService: Ws1MessageService,
      private cookieService : CookieService,
      private csrfSvc : CSRFService,
    ) {
    }

    onEnteredMessage($event){
        console.log($event);
        this.ws1MessageService.sendMessage($event, this.account);

    }
    ngOnInit() {
        this.principal.isAuthenticated() ? this.principal.identity().then((account) => {
            this.account = account;
            this.isAnon=false;
            console.log("DEBUG",account);
        }) : this.account = new Account(
            false, [], "","","","","anonymous-"+this.csrfSvc.getCSRF().split('-')[0],"");
        this.registerAuthenticationSuccess();
        this.toggled = false;
        //TRACKER WEBSOCKET
        this.ws1MessageService.connect().then(queue => {this.activities = queue});
        this.ws1MessageService.subscribe();

        this.ws1MessageService.receive().subscribe((message) => {
            this.displayMessages(message);
        });
    }
    ngOnDestroy() {
        this.ws1MessageService.unsubscribe();
    }
    displayMessages(message: any) {
            this.activities.push(message);
            this.scrollToBottom();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    scrollToBottom(): void {
       /* console.log("Scrolling", this.myScrollContainer);
        try {
            this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
        } catch(err) { }*/
    }
}
