import {Directive} from "@angular/core";
import {OpTemplateService} from "./op-templates.service";
//import {OperationComponent} from './playground.component';

@Directive({
  selector: '[hjl-dnd]',
})
export class DragNDropDirective {
     static opConnectionHolder: HTMLElement[] = [];
     static tpConnectionHolder = {
         tpItem : null,
         op : null
     };
     static lineId = 1;
     self: any;
    div: HTMLElement;
    constructor(private optpService: OpTemplateService) {
        // DragNDropDirective.opConnectionHolder = [];
        this.self = this;

    }
    static getOpTpId(div){
        return DragNDropDirective.closest(div,['op-tp', 'io-op-main'], 6).id;
    }
    static resetOpConnection() {
        DragNDropDirective.opConnectionHolder = []
    }

    static initOpConnection(div) {
        const opContainer = document.body.getElementsByClassName('op-content')[0];
        let ret = [];
        if (this.opConnectionHolder.length >= 1) {
            const divId = div.id ? div.id : this.getOpTpId(div);
            const holdDivId = this.opConnectionHolder[0].id ? this.opConnectionHolder[0].id : this.getOpTpId(this.opConnectionHolder[0]);
            if( divId.substring(1)===holdDivId.substring(1)) {
                console.log("same id", div, this.opConnectionHolder[0]);
                this.opConnectionHolder[0] = div;
                return [];
            }
            const div1OpType = this.opConnectionHolder[0].classList.contains('i-op') ?  'I' : this.opConnectionHolder[0].parentElement.classList.contains('i-op') ? 'I' : 'O';
            const div2OpType = div.classList.contains('i-op') ? 'I' : div.parentElement.classList.contains('i-op') ? 'I' : 'O';
            console.log("GOTCHA", div1OpType, div2OpType);

            if (div1OpType === 'I' && div2OpType === 'O') {

                console.log("PULL", div, this.opConnectionHolder[0]);
                const lineId = this.drawLine(div, this.opConnectionHolder[0],"#364b7c9c", 3, opContainer);
                ret=[holdDivId, divId, lineId];
                this.opConnectionHolder = [];

            } else if (div1OpType === 'O' && div2OpType === 'I') {

                console.log("PULL", div, this.opConnectionHolder[0]);
                const lineId = this.drawLine(this.opConnectionHolder[0], div, "#364b7c9c", 3, opContainer);
                ret=[divId, holdDivId, lineId];
                this.opConnectionHolder = [];
            } else {

                this.opConnectionHolder[0] = div;
                console.log("REPUSHING", this.opConnectionHolder[0]);
            }
        } else {
            console.log("PUSH", div);
            this.opConnectionHolder.push(div);
        }
        return ret;
    }
    static drawLine(div, div1, color, thickness, container) {
        DragNDropDirective.lineId = DragNDropDirective.lineId + 1;
        const containerOff = this.getOffset(container, null);
        const off1 = this.getOffset(div, containerOff);
        const off2 = this.getOffset(div1, containerOff);
        /*
        get position of I and O
        consider getting the RELATIVE position and append the line to the container
         */
        // middle right
        const x1 = off1.left + off1.width;
        const y1 = off1.top + off1.height/2;
        // middle left
        const x2 = off2.left ;// + off2.width;
        const y2 = off2.top + off2.height/2;
        // distance
        const length = Math.sqrt(((x2-x1) * (x2-x1)) + ((y2-y1) * (y2-y1)));
        // center
        const cx = ((x1 + x2) / 2) - (length / 2);
        const cy = ((y1 + y2) / 2) - (thickness / 2);
        // angle
        const angle = Math.atan2((y1-y2),(x1-x2))*(180/Math.PI);
        // make hr
        const htmlLine = "<div id='op-line-"+ DragNDropDirective.lineId +"' style='padding:0px; margin:0px; height:" + thickness + "px; background-color:"
            + color + "; line-height:1px; position:absolute; left:" + cx + "px; top:" + cy + "px; width:" + length + "px; -moz-transform:rotate(" + angle + "deg);" +
            " -webkit-transform:rotate(" + angle + "deg);" + " -o-transform:rotate(" + angle + "deg); -ms-transform:rotate(" + angle + "deg); transform:rotate(" + angle + "deg);' />";
        const mydiv = container || document.body;
        const newcontent = document.createElement('div');
        newcontent.innerHTML = htmlLine;

       // while (newcontent.firstChild) {
            mydiv.appendChild(newcontent.firstChild);
      //  }
        return "op-line-"+DragNDropDirective.lineId;
    }

    static getOffset( el: HTMLElement, containerOffset: any) {
        let rect;
        if (containerOffset!=null) {
            console.log("CASE0");
            const IO  = el;//el.className=='io' ? el : el.parentElement.className=='io' ? el.parentElement : el.parentElement.getElementsByClassName('io')[0] ;
            rect = IO.getBoundingClientRect();
            return {
                left: rect.left +  window.pageXOffset - (containerOffset['left'] || 0),
                top: rect.top + window.pageYOffset - (containerOffset['top'] || 0),
                width: (rect.width || el.offsetWidth), // - (containerOffset.width || 0),
                height: (rect.height || el.offsetHeight)// - (containerOffset.height || 0)
            };
        } else {
            console.log("CASE1");
            rect = el.getBoundingClientRect();

            return {
                left: rect.left + window.pageXOffset,
                top: rect.top + window.pageYOffset,
                width: (rect.width || el.offsetWidth), // - (containerOffset.width || 0),
                height: (rect.height || el.offsetHeight)// - (containerOffset.height || 0)
            };
        }
    }



    static showMore(div) {
        console.log('SHOWMORE', div);
    div.parentElement.classList.add('op-overoverlay');
    div.getElementsByClassName('content')[0].classList.add('open');
   document.body.getElementsByClassName('op-overlay')[0].classList.add('open');
}
  static closest(e, classList, count) {
      if (count==0 || e[0] && e[0].nodeName == "HTML") {
          return null;
      } else if (e.classList ? classList.some(function(cl){return e.classList.contains(cl)}) : false) {
          return e;
      } else {
          return DragNDropDirective.closest(e.parentElement, classList,count-1);
      }

  }

  static resetTpConnection() {
        const temp = DragNDropDirective.tpConnectionHolder;
      DragNDropDirective.tpConnectionHolder = {
          tpItem : null,
          op : null
      };
      return temp;
  }
    static initTpConnection(elem, origin) {
        console.log("INIT TP CONNECTION", elem, origin);
        //TODO check integrity
        if (origin=='menu') {
            DragNDropDirective.tpConnectionHolder.op = elem;
        } else {
            DragNDropDirective.tpConnectionHolder.tpItem =elem;
        }
        if (DragNDropDirective.tpConnectionHolder.op && DragNDropDirective.tpConnectionHolder.tpItem) {
            //link
            const type = DragNDropDirective.tpConnectionHolder.op.active.input ? 'IN' : 'OUT';
           DragNDropDirective.tpConnectionHolder.tpItem.parentElement.querySelector('.tp-input-linked-op').innerText =
              DragNDropDirective.tpConnectionHolder.op.operationName+' ::'+type;
            DragNDropDirective.tpConnectionHolder.tpItem.dataset.op = DragNDropDirective.tpConnectionHolder.op;
            DragNDropDirective.tpConnectionHolder.tpItem.parentElement.classList.add('tp-linked');
            DragNDropDirective.tpConnectionHolder.tpItem.dataset.io=type;
               //.insertAfter('data-tp-io-operation', DragNDropDirective.tpConnectionHolder.op.title)
            return DragNDropDirective.resetTpConnection();
        }
        console.log("INIT TP CONNECTION incomplete", DragNDropDirective.tpConnectionHolder);
        return null;

    }


}
/*
Copyright 2017 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/
