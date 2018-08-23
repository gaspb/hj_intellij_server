import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {DatePipe} from "@angular/common";

import {
    AccountService,
    AuthServerProvider,
    CSRFService,
    GtwSharedCommonModule,
    GtwSharedLibsModule,
    HasAnyAuthorityDirective,
    JhiLoginModalComponent,
    JhiTrackerService,
    LoginModalService,
    LoginService,
    Principal,
    StackService,
    StateStorageService,
    UserService,
    Ws1MessageService
} from "./";

@NgModule({
    imports: [
        GtwSharedLibsModule,
        GtwSharedCommonModule
    ],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        JhiTrackerService,
        Ws1MessageService,
        AuthServerProvider,
        StackService,
        UserService,
        DatePipe
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        GtwSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class GtwSharedModule {}
