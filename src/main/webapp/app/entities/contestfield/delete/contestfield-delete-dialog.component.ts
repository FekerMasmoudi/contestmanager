import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IContestfield } from '../contestfield.model';
import { ContestfieldService } from '../service/contestfield.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './contestfield-delete-dialog.component.html',
})
export class ContestfieldDeleteDialogComponent {
  contestfield?: IContestfield;

  constructor(protected contestfieldService: ContestfieldService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.contestfieldService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
