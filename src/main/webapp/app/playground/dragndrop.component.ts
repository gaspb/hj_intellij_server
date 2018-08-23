import {AfterViewInit, Component, ComponentFactoryResolver, OnDestroy} from "@angular/core";

@Component({
  selector: 'drag-n-drop',
  template: `
      <script src='drag_drop.js'></script>
              <div class="drop-container" onclick="init();">
                <h3>Drop container</h3>
                  <canvas id="canvas"></canvas>
                  <div class="container">
                      <div class="row">
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                      </div>
                      <div class="row">
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                      </div>
                      <div class="row">
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                          <div class="col-md-1 grid-op"></div>
                      </div>
                  </div>
              </div>
            `
})
export class DragNDropComponent implements AfterViewInit, OnDestroy {
 //  @Input() mstemplates: MSTemplateConfigItem[];
  currentAddIndex: number = -1;
  // @ViewChild(msTemplateDirective) msTemplateHost: msTemplateDirective;
  subscription: any;
  interval: any;

  constructor(private componentFactoryResolver: ComponentFactoryResolver) { }

  ngAfterViewInit() {

  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }
}

/*
Copyright 2017 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/
