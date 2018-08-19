import {Component, OnInit} from "@angular/core";
import {MSTemplateService} from "./mstemplate.service";
import {MSTemplateConfigItem} from "./mstemplate-config-item";
import {GatewayRoute} from "../admin/gateway/gateway-route.model";
import {MsEntry} from "./ms-entry.model";

@Component({
  selector: 'msgateway-root',
  template: `
    <div>
        MSGatewayRoot
        <div>{{msEntries.length}}</div>
        <div *ngFor="let route of msEntries">
            <div>{{route.path}}</div>
            <div>{{route.gtwayProperties}}</div>
        </div>
      <app-mstemplates-render [mstemplates]="mstemplates"></app-mstemplates-render>
    </div>
  `
})
export class MSGatewayRootComponent implements OnInit {
    mstemplates: MSTemplateConfigItem[];
    gatewayRoutes: GatewayRoute[];
    msEntries: MsEntry[];

    constructor(private mmTemplateService: MSTemplateService) {
    }

    ngOnInit() {
        this.mstemplates = this.mmTemplateService.getMsTemplates();
        this.msEntries = this.mmTemplateService.loadTemplates();


    }
}

/*
Copyright 2017 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/
