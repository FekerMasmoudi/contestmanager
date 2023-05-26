import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SpecialityComponent } from './list/speciality.component';
import { SpecialityDetailComponent } from './detail/speciality-detail.component';
import { SpecialityUpdateComponent } from './update/speciality-update.component';
import { SpecialityDeleteDialogComponent } from './delete/speciality-delete-dialog.component';
import { SpecialityRoutingModule } from './route/speciality-routing.module';

@NgModule({
  imports: [SharedModule, SpecialityRoutingModule],
  declarations: [SpecialityComponent, SpecialityDetailComponent, SpecialityUpdateComponent, SpecialityDeleteDialogComponent],
})
export class SpecialityModule {}
