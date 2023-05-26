import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GeneralrulesComponent } from '../list/generalrules.component';
import { GeneralrulesDetailComponent } from '../detail/generalrules-detail.component';
import { GeneralrulesUpdateComponent } from '../update/generalrules-update.component';
import { GeneralrulesRoutingResolveService } from './generalrules-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const generalrulesRoute: Routes = [
  {
    path: '',
    component: GeneralrulesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GeneralrulesDetailComponent,
    resolve: {
      generalrules: GeneralrulesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GeneralrulesUpdateComponent,
    resolve: {
      generalrules: GeneralrulesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GeneralrulesUpdateComponent,
    resolve: {
      generalrules: GeneralrulesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(generalrulesRoute)],
  exports: [RouterModule],
})
export class GeneralrulesRoutingModule {}
