import {AfterViewInit, Component, ComponentFactoryResolver, Input, OnDestroy, ViewChild} from "@angular/core";

import {msTemplateDirective} from "./mstemplate.directive";
import {MSTemplateConfigItem} from "./mstemplate-config-item";
import {MSTemplateComponent} from "./mstemplate.component";

@Component({
  selector: 'app-mstemplates-render',
  template: `
              <div class="ad-banner">
                <h3>Microservices generated front-end :</h3>
                <ng-template mstemplate-host></ng-template>
              </div>
            `
})
export class MsTemplateRenderComponent implements AfterViewInit, OnDestroy {
  @Input() mstemplates: MSTemplateConfigItem[];
  currentAddIndex: number = -1;
  @ViewChild(msTemplateDirective) msTemplateHost: msTemplateDirective;
  subscription: any;
  interval: any;

  constructor(private componentFactoryResolver: ComponentFactoryResolver) { }

  ngAfterViewInit() {
    this.loadComponent();
    this.getMsTemplates();
  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }

  loadComponent() {
    this.currentAddIndex = (this.currentAddIndex + 1) % this.mstemplates.length;
    let msTemplateItem = this.mstemplates[this.currentAddIndex];

    let componentFactory = this.componentFactoryResolver.resolveComponentFactory(msTemplateItem.component);

    let viewContainerRef = this.msTemplateHost.viewContainerRef;
    viewContainerRef.clear();

    let componentRef = viewContainerRef.createComponent(componentFactory);
    (<MSTemplateComponent>componentRef.instance).data = msTemplateItem.data;
  }

    getMsTemplates() {
    this.interval = setInterval(() => {
      this.loadComponent();
    }, 3000);
  }
}


/*
Copyright 2017 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/
