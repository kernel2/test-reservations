import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Reference } from '../../models/reference';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-payment-modal',
    standalone: true,
    imports: [
        ReactiveFormsModule,
        FormsModule,
        CommonModule
    ],
    templateUrl: './payment-modal.component.html',
})
export class PaymentModalComponent {

    @Input() paymentMethods: Reference[] = [];
    paymentForm: FormGroup;

    selectedPaymentMethod: string | null = null;
    constructor(
        private formBuilder: FormBuilder,
        private activeMdoal: NgbActiveModal) {
        this.paymentForm = this.formBuilder.group({
            paymentMethod: [null, Validators.required]
        });
    }

    confirmPayment() {
        if (this.paymentForm.valid) {
            this.activeMdoal.close(this.paymentForm.value.paymentMethod);
        }
    }

    close() {
        this.activeMdoal.dismiss(null);
    }

}