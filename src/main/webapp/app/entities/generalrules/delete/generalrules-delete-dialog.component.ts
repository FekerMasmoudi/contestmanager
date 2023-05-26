import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGeneralrules } from '../generalrules.model';
import { GeneralrulesService } from '../service/generalrules.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './generalrules-delete-dialog.component.html',
})
export class GeneralrulesDeleteDialogComponent {
  generalrules?: IGeneralrules;

  constructor(protected generalrulesService: GeneralrulesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.generalrulesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
