import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ContestannounceComponent } from './list/contestannounce.component';
import { ContestannounceDetailComponent } from './detail/contestannounce-detail.component';
import { ContestannounceUpdateComponent } from './update/contestannounce-update.component';
import { ContestannounceDeleteDialogComponent } from './delete/contestannounce-delete-dialog.component';
import { ContestannounceRoutingModule } from './route/contestannounce-routing.module';

@NgModule({
  imports: [SharedModule, ContestannounceRoutingModule],
  declarations: [
    ContestannounceComponent,
    ContestannounceDetailComponent,
    ContestannounceUpdateComponent,
    ContestannounceDeleteDialogComponent,
  ],
})
export class ContestannounceModule {}
