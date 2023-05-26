import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EducationlevelComponent } from './list/educationlevel.component';
import { EducationlevelDetailComponent } from './detail/educationlevel-detail.component';
import { EducationlevelUpdateComponent } from './update/educationlevel-update.component';
import { EducationlevelDeleteDialogComponent } from './delete/educationlevel-delete-dialog.component';
import { EducationlevelRoutingModule } from './route/educationlevel-routing.module';

@NgModule({
  imports: [SharedModule, EducationlevelRoutingModule],
  declarations: [
    EducationlevelComponent,
    EducationlevelDetailComponent,
    EducationlevelUpdateComponent,
    EducationlevelDeleteDialogComponent,
  ],
})
export class EducationlevelModule {}
