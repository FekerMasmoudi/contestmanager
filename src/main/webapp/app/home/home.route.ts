import { Route } from '@angular/router';

import { HomeComponent } from './home.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    pageTitle: 'هلا وسهلا بك، في منصـة الإنتدبـات للشركة الجهوية للنقل بصفاقس',
  },
};
