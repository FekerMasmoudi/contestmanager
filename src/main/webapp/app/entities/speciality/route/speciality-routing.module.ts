import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SpecialityComponent } from '../list/speciality.component';
import { SpecialityDetailComponent } from '../detail/speciality-detail.component';
import { SpecialityUpdateComponent } from '../update/speciality-update.component';
import { SpecialityRoutingResolveService } from './speciality-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const specialityRoute: Routes = [
  {
    path: '',
    component: SpecialityComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpecialityDetailComponent,
    resolve: {
      speciality: SpecialityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpecialityUpdateComponent,
    resolve: {
      speciality: SpecialityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpecialityUpdateComponent,
    resolve: {
      speciality: SpecialityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(specialityRoute)],
  exports: [RouterModule],
})
export class SpecialityRoutingModule {}
