import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FieldsComponent } from './list/fields.component';
import { FieldsDetailComponent } from './detail/fields-detail.component';
import { FieldsUpdateComponent } from './update/fields-update.component';
import { FieldsDeleteDialogComponent } from './delete/fields-delete-dialog.component';
import { FieldsRoutingModule } from './route/fields-routing.module';

@NgModule({
  imports: [SharedModule, FieldsRoutingModule],
  declarations: [FieldsComponent, FieldsDetailComponent, FieldsUpdateComponent, FieldsDeleteDialogComponent],
})
export class FieldsModule {}
