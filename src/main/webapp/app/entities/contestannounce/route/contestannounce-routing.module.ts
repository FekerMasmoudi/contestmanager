import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContestannounceComponent } from '../list/contestannounce.component';
import { ContestannounceDetailComponent } from '../detail/contestannounce-detail.component';
import { ContestannounceUpdateComponent } from '../update/contestannounce-update.component';
import { ContestannounceRoutingResolveService } from './contestannounce-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const contestannounceRoute: Routes = [
  {
    path: '',
    component: ContestannounceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContestannounceDetailComponent,
    resolve: {
      contestannounce: ContestannounceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContestannounceUpdateComponent,
    resolve: {
      contestannounce: ContestannounceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContestannounceUpdateComponent,
    resolve: {
      contestannounce: ContestannounceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contestannounceRoute)],
  exports: [RouterModule],
})
export class ContestannounceRoutingModule {}
