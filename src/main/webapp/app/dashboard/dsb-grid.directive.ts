/**
 * Created by High Jack on 23/06/2018.
 */
import {Directive, ElementRef, HostListener, OnInit} from "@angular/core";

@Directive({
    selector: '[dsbGrid]'
})
export class DsbGridDirective implements OnInit{
    toggled = false;
    constructor(private el: ElementRef) {
       // el.nativeElement.style.backgroundColor = 'yellow';

    }
    //CAN PASS @INPUT TO THE COMPONENT IF NEEDED


    ngOnInit(): void {
        let arr= this.el.nativeElement.querySelectorAll('input');
        for (let x =0; x<arr.length;x++) {
            this.resizeInput(arr[x]);
        }
    }
    @HostListener('click') onClick() {
        this.toggle();
    }
    @HostListener('input',  ['$event.target']) onInput(input) {
        this.resizeInput(input);
    }

    private toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.el.nativeElement.classList.add('dsb-toggled');
        } else {
            this.el.nativeElement.classList.remove('dsb-toggled');
        }

    }
    private resizeInput(el) {
    el.style.width = el.value.length+1 + "ch";
    }




}
