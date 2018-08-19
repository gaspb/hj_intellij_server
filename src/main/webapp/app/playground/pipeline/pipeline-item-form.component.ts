import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {PlaygroundService} from "../playground.service";

@Component({
    selector: 'pipeline-item-form',
    templateUrl: './pipeline-item-form.component.html',


})
export class PipelineItemFormComponent implements OnInit {

    @Output() completeForm: EventEmitter<any> = new EventEmitter<any>();
    formItem;

    @Input('formType')
    formType;
    @Input('formActive')
    formActive;
    ops:any;
    bdts:any;//big data transformations

    constructor(private pgService: PlaygroundService
    ) {
    }

    ngOnInit() {
        this.formType = "";
        this.formItem = {};
        this.getDataTransformations(); //todo check formType when loaded
    }

    log(...any) {
            console.log("PIPELINE_FORM_LOGGER", any);
    }

    getOps() {
        this.pgService.getOperationList().first().subscribe(data => { console.log("EMIT ---- GET OPS ",data);this.ops = data;});

    }
    getDataTransformations() {
        this.bdts = [
            'Linear regression', 'Reduce', 'Map', 'Thresholds and limits', 'Alerts', 'Image recognition', 'Image sanitizing', '+'
        ]
    }

    selectOp(name) {
        this.formItem.data = this.ops.filter(item=>item.operationName = name)[0];
        this.completeForm.emit(this.formItem);
    }

    selectBdt(name) {
        this.formItem.name = name;
        this.completeForm.emit(this.formItem);
    }

}
