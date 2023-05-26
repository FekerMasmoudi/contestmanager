import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEducationlevel } from '../educationlevel.model';
import { EducationlevelService } from '../service/educationlevel.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './educationlevel-delete-dialog.component.html',
})
export class EducationlevelDeleteDialogComponent {
  educationlevel?: IEducationlevel;

  constructor(protected educationlevelService: EducationlevelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.educationlevelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
