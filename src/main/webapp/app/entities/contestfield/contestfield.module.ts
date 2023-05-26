import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ContestfieldComponent } from './list/contestfield.component';
import { ContestfieldDetailComponent } from './detail/contestfield-detail.component';
import { ContestfieldUpdateComponent } from './update/contestfield-update.component';
import { ContestfieldDeleteDialogComponent } from './delete/contestfield-delete-dialog.component';
import { ContestfieldRoutingModule } from './route/contestfield-routing.module';

@NgModule({
  imports: [SharedModule, ContestfieldRoutingModule],
  declarations: [ContestfieldComponent, ContestfieldDetailComponent, ContestfieldUpdateComponent, ContestfieldDeleteDialogComponent],
})
export class ContestfieldModule {}
