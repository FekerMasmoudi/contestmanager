import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EducationlevelComponent } from '../list/educationlevel.component';
import { EducationlevelDetailComponent } from '../detail/educationlevel-detail.component';
import { EducationlevelUpdateComponent } from '../update/educationlevel-update.component';
import { EducationlevelRoutingResolveService } from './educationlevel-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const educationlevelRoute: Routes = [
  {
    path: '',
    component: EducationlevelComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EducationlevelDetailComponent,
    resolve: {
      educationlevel: EducationlevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EducationlevelUpdateComponent,
    resolve: {
      educationlevel: EducationlevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EducationlevelUpdateComponent,
    resolve: {
      educationlevel: EducationlevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(educationlevelRoute)],
  exports: [RouterModule],
})
export class EducationlevelRoutingModule {}
