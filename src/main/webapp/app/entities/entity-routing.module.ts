import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'fields',
        data: { pageTitle: 'Fields' },
        loadChildren: () => import('./fields/fields.module').then(m => m.FieldsModule),
      },
      {
        path: 'educationlevel',
        data: { pageTitle: 'Educationlevels' },
        loadChildren: () => import('./educationlevel/educationlevel.module').then(m => m.EducationlevelModule),
      },
      {
        path: 'certificate',
        data: { pageTitle: 'Certificates' },
        loadChildren: () => import('./certificate/certificate.module').then(m => m.CertificateModule),
      },
      {
        path: 'grade',
        data: { pageTitle: 'Grades' },
        loadChildren: () => import('./grade/grade.module').then(m => m.GradeModule),
      },
      {
        path: 'sector',
        data: { pageTitle: 'Sectors' },
        loadChildren: () => import('./sector/sector.module').then(m => m.SectorModule),
      },
      {
        path: 'speciality',
        data: { pageTitle: 'Specialities' },
        loadChildren: () => import('./speciality/speciality.module').then(m => m.SpecialityModule),
      },
      {
        path: 'generalrules',
        data: { pageTitle: 'Generalrules' },
        loadChildren: () => import('./generalrules/generalrules.module').then(m => m.GeneralrulesModule),
      },
      {
        path: 'contestannounce',
        data: { pageTitle: 'Contestannounces' },
        loadChildren: () => import('./contestannounce/contestannounce.module').then(m => m.ContestannounceModule),
      },
      {
        path: 'contest',
        data: { pageTitle: 'Contests' },
        loadChildren: () => import('./contest/contest.module').then(m => m.ContestModule),
      },
      {
        path: 'contestfield',
        data: { pageTitle: 'Contestfields' },
        loadChildren: () => import('./contestfield/contestfield.module').then(m => m.ContestfieldModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
