import "./vendor.ts";

import {Injector, NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {Ng2Webstorage} from "ngx-webstorage";
import {JhiEventManager} from "ng-jhipster";

import {AuthExpiredInterceptor} from "./blocks/interceptor/auth-expired.interceptor";
import {ErrorHandlerInterceptor} from "./blocks/interceptor/errorhandler.interceptor";
import {NotificationInterceptor} from "./blocks/interceptor/notification.interceptor";
import {GtwSharedModule, UserRouteAccessService} from "./shared";
import {GtwAppRoutingModule} from "./app-routing.module";
import {GtwHomeModule} from "./home/home.module";
import {GtwAdminModule} from "./admin/admin.module";
import {GtwAccountModule} from "./account/account.module";
import {GtwEntityModule} from "./entities/entity.module";
import {PaginationConfig} from "./blocks/config/uib-pagination.config";
// jhipster-needle-angular-add-module-import JHipster will add new module here
import {
    ErrorComponent,
    FooterComponent,
    JhiMainComponent,
    NavbarComponent,
    PageRibbonComponent,
    ProfileService
} from "./layouts";
import {GtwIotModule} from "./iot/iot.module";
import {GtwWs1Module} from "./demos/realtime/ws1.module";

import {MSTemplateModule} from "./framework/mstemplate.module";
import {PlaygroundModule} from "./playground/playground.module";
import {StackComponent} from "./layouts/stack/stack.component";
import {GtwDocumentationModule} from "./documentation/documentation.module";
import {GtwDashboardModule} from "./dashboard/dashboard.module";
import {GtwRepoModule} from "./repository/repo.module";


@NgModule({
    imports: [
        BrowserModule,
        GtwAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        GtwSharedModule,
        GtwHomeModule,
        GtwAdminModule,
        GtwAccountModule,
        GtwEntityModule,
        GtwIotModule,
        GtwWs1Module,
        MSTemplateModule,
        PlaygroundModule,
        GtwDocumentationModule,
        GtwDashboardModule,
        GtwRepoModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        StackComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        PaginationConfig,
        UserRouteAccessService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [
                JhiEventManager
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        }
    ],
    bootstrap: [ JhiMainComponent ]
})
export class GtwAppModule {}
