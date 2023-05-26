import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GeneralrulesComponent } from './list/generalrules.component';
import { GeneralrulesDetailComponent } from './detail/generalrules-detail.component';
import { GeneralrulesUpdateComponent } from './update/generalrules-update.component';
import { GeneralrulesDeleteDialogComponent } from './delete/generalrules-delete-dialog.component';
import { GeneralrulesRoutingModule } from './route/generalrules-routing.module';

@NgModule({
  imports: [SharedModule, GeneralrulesRoutingModule],
  declarations: [GeneralrulesComponent, GeneralrulesDetailComponent, GeneralrulesUpdateComponent, GeneralrulesDeleteDialogComponent],
})
export class GeneralrulesModule {}
