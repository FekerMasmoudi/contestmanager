import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EducationlevelFormService, EducationlevelFormGroup } from './educationlevel-form.service';
import { IEducationlevel } from '../educationlevel.model';
import { EducationlevelService } from '../service/educationlevel.service';

@Component({
  selector: 'jhi-educationlevel-update',
  templateUrl: './educationlevel-update.component.html',
})
export class EducationlevelUpdateComponent implements OnInit {
  isSaving = false;
  educationlevel: IEducationlevel | null = null;

  editForm: EducationlevelFormGroup = this.educationlevelFormService.createEducationlevelFormGroup();

  constructor(
    protected educationlevelService: EducationlevelService,
    protected educationlevelFormService: EducationlevelFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ educationlevel }) => {
      this.educationlevel = educationlevel;
      if (educationlevel) {
        this.updateForm(educationlevel);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const educationlevel = this.educationlevelFormService.getEducationlevel(this.editForm);
    if (educationlevel.id !== null) {
      this.subscribeToSaveResponse(this.educationlevelService.update(educationlevel));
    } else {
      this.subscribeToSaveResponse(this.educationlevelService.create(educationlevel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEducationlevel>>): void {
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

  protected updateForm(educationlevel: IEducationlevel): void {
    this.educationlevel = educationlevel;
    this.educationlevelFormService.resetForm(this.editForm, educationlevel);
  }
}
