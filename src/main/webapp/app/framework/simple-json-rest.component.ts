import {Component, Input} from "@angular/core";

import {MSTemplateComponent} from "./mstemplate.component";

@Component({
  template: `
    <div class="simple-json-rest">
        <h4>Simple JSON REST Template</h4>
        <h4>{{data.title}}</h4> 
      
      {{data.body}}
    </div>
  `
})
export class SimpleJsonRestComponent implements MSTemplateComponent {
  @Input() data: any;

}



/*
Copyright 2017 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/
