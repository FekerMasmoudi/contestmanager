import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IContestannounce } from '../contestannounce.model';
import { ContestannounceService } from '../service/contestannounce.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './contestannounce-delete-dialog.component.html',
})
export class ContestannounceDeleteDialogComponent {
  contestannounce?: IContestannounce;

  constructor(protected contestannounceService: ContestannounceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.contestannounceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
