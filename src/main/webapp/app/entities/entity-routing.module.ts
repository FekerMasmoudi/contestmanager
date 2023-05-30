import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'fields',
        data: { pageTitle: 'الحقــــــــــــــــــــــــــول' },
        loadChildren: () => import('./fields/fields.module').then(m => m.FieldsModule),
      },
      {
        path: 'generalrules',
        data: { pageTitle: 'الشــروط العــامـــــة' },
        loadChildren: () => import('./generalrules/generalrules.module').then(m => m.GeneralrulesModule),
      },
      {
        path: 'contestannounce',
        data: { pageTitle: 'إعــلان المنــــاظارات' },
        loadChildren: () => import('./contestannounce/contestannounce.module').then(m => m.ContestannounceModule),
      },
      {
        path: 'grade',
        data: { pageTitle: 'الرتبــــــــــــــــــــــــــــــــــة' },
        loadChildren: () => import('./grade/grade.module').then(m => m.GradeModule),
      },
      {
        path: 'sector',
        data: { pageTitle: 'المســـــــــــــــــــــــــــــلك' },
        loadChildren: () => import('./sector/sector.module').then(m => m.SectorModule),
      },
      {
        path: 'speciality',
        data: { pageTitle: 'الاختصـــــــــــــــــــــاص' },
        loadChildren: () => import('./speciality/speciality.module').then(m => m.SpecialityModule),
      },
      {
        path: 'educationlevel',
        data: { pageTitle: 'المستــوى التعليمــي' },
        loadChildren: () => import('./educationlevel/educationlevel.module').then(m => m.EducationlevelModule),
      },
      {
        path: 'certificate',
        data: { pageTitle: 'الشّهـــــــــــــــــــــــــــادة' },
        loadChildren: () => import('./certificate/certificate.module').then(m => m.CertificateModule),
      },
      {
        path: 'contest',
        data: { pageTitle: 'المنــاظـــــــــــــــــــــــــــرة' },
        loadChildren: () => import('./contest/contest.module').then(m => m.ContestModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
