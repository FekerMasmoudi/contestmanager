import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { GradeFormService, GradeFormGroup } from './grade-form.service';
import { IGrade } from '../grade.model';
import { GradeService } from '../service/grade.service';
import { IEducationlevel } from 'app/entities/educationlevel/educationlevel.model';
import { EducationlevelService } from 'app/entities/educationlevel/service/educationlevel.service';
import { ICertificate } from 'app/entities/certificate/certificate.model';
import { CertificateService } from 'app/entities/certificate/service/certificate.service';

@Component({
  selector: 'jhi-grade-update',
  templateUrl: './grade-update.component.html',
})
export class GradeUpdateComponent implements OnInit {
  isSaving = false;
  grade: IGrade | null = null;

  educationlevelsSharedCollection: IEducationlevel[] = [];
  certificatesSharedCollection: ICertificate[] = [];

  editForm: GradeFormGroup = this.gradeFormService.createGradeFormGroup();

  constructor(
    protected gradeService: GradeService,
    protected gradeFormService: GradeFormService,
    protected educationlevelService: EducationlevelService,
    protected certificateService: CertificateService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEducationlevel = (o1: IEducationlevel | null, o2: IEducationlevel | null): boolean =>
    this.educationlevelService.compareEducationlevel(o1, o2);

  compareCertificate = (o1: ICertificate | null, o2: ICertificate | null): boolean => this.certificateService.compareCertificate(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grade }) => {
      this.grade = grade;
      if (grade) {
        this.updateForm(grade);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const grade = this.gradeFormService.getGrade(this.editForm);
    if (grade.id !== null) {
      this.subscribeToSaveResponse(this.gradeService.update(grade));
    } else {
      this.subscribeToSaveResponse(this.gradeService.create(grade));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrade>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(grade: IGrade): void {
    this.grade = grade;
    this.gradeFormService.resetForm(this.editForm, grade);

    this.educationlevelsSharedCollection = this.educationlevelService.addEducationlevelToCollectionIfMissing<IEducationlevel>(
      this.educationlevelsSharedCollection,
      grade.ideducationlevel
    );
    this.certificatesSharedCollection = this.certificateService.addCertificateToCollectionIfMissing<ICertificate>(
      this.certificatesSharedCollection,
      grade.idcertificate
    );
  }

  protected loadRelationshipsOptions(): void {
    this.educationlevelService
      .query()
      .pipe(map((res: HttpResponse<IEducationlevel[]>) => res.body ?? []))
      .pipe(
        map((educationlevels: IEducationlevel[]) =>
          this.educationlevelService.addEducationlevelToCollectionIfMissing<IEducationlevel>(educationlevels, this.grade?.ideducationlevel)
        )
      )
      .subscribe((educationlevels: IEducationlevel[]) => (this.educationlevelsSharedCollection = educationlevels));

    this.certificateService
      .query()
      .pipe(map((res: HttpResponse<ICertificate[]>) => res.body ?? []))
      .pipe(
        map((certificates: ICertificate[]) =>
          this.certificateService.addCertificateToCollectionIfMissing<ICertificate>(certificates, this.grade?.idcertificate)
        )
      )
      .subscribe((certificates: ICertificate[]) => (this.certificatesSharedCollection = certificates));
  }
}
