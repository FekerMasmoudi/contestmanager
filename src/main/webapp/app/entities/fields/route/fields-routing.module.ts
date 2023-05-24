import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FieldsComponent } from '../list/fields.component';
import { FieldsDetailComponent } from '../detail/fields-detail.component';
import { FieldsUpdateComponent } from '../update/fields-update.component';
import { FieldsRoutingResolveService } from './fields-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fieldsRoute: Routes = [
  {
    path: '',
    component: FieldsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FieldsDetailComponent,
    resolve: {
      fields: FieldsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FieldsUpdateComponent,
    resolve: {
      fields: FieldsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FieldsUpdateComponent,
    resolve: {
      fields: FieldsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fieldsRoute)],
  exports: [RouterModule],
})
export class FieldsRoutingModule {}
