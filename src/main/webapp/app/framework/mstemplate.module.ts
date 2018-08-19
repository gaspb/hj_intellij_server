import {BrowserModule} from "@angular/platform-browser";
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {MSGatewayRootComponent} from "./msgatewayroot.component";
import {SimpleJsonRestComponent} from "./simple-json-rest.component";
import {MsTemplateRenderComponent} from "./mstemplates-render.component";
import {HeroProfileComponent} from "./hero-profile.component";
import {msTemplateDirective} from "./mstemplate.directive";
import {MSTemplateService} from "./mstemplate.service";
import {GtwSharedModule} from "../shared";
import {RouterModule} from "@angular/router";
import {MSTEMPLATE_ROUTE} from "./mstemplate.route";

@NgModule({
  imports: [ BrowserModule,
      GtwSharedModule,
      RouterModule.forRoot([ MSTEMPLATE_ROUTE ], { useHash: true })],
  providers: [MSTemplateService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [ MSGatewayRootComponent,
      MsTemplateRenderComponent,
      SimpleJsonRestComponent,
                  HeroProfileComponent,
      msTemplateDirective ],
  entryComponents: [ SimpleJsonRestComponent, HeroProfileComponent ],
  bootstrap: [ MSGatewayRootComponent ]
})
export class MSTemplateModule {
  constructor() {}
}

/*
Copyright 2017 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/
