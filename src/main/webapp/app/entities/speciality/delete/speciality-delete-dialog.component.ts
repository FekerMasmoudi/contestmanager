import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpeciality } from '../speciality.model';
import { SpecialityService } from '../service/speciality.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './speciality-delete-dialog.component.html',
})
export class SpecialityDeleteDialogComponent {
  speciality?: ISpeciality;

  constructor(protected specialityService: SpecialityService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.specialityService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
