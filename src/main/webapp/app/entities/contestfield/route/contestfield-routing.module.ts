import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContestfieldComponent } from '../list/contestfield.component';
import { ContestfieldDetailComponent } from '../detail/contestfield-detail.component';
import { ContestfieldUpdateComponent } from '../update/contestfield-update.component';
import { ContestfieldRoutingResolveService } from './contestfield-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const contestfieldRoute: Routes = [
  {
    path: '',
    component: ContestfieldComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContestfieldDetailComponent,
    resolve: {
      contestfield: ContestfieldRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContestfieldUpdateComponent,
    resolve: {
      contestfield: ContestfieldRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContestfieldUpdateComponent,
    resolve: {
      contestfield: ContestfieldRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contestfieldRoute)],
  exports: [RouterModule],
})
export class ContestfieldRoutingModule {}
