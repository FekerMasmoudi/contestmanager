import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { GeneralrulesFormService, GeneralrulesFormGroup } from './generalrules-form.service';
import { IGeneralrules } from '../generalrules.model';
import { GeneralrulesService } from '../service/generalrules.service';

@Component({
  selector: 'jhi-generalrules-update',
  templateUrl: './generalrules-update.component.html',
})
export class GeneralrulesUpdateComponent implements OnInit {
  isSaving = false;
  generalrules: IGeneralrules | null = null;

  editForm: GeneralrulesFormGroup = this.generalrulesFormService.createGeneralrulesFormGroup();

  constructor(
    protected generalrulesService: GeneralrulesService,
    protected generalrulesFormService: GeneralrulesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ generalrules }) => {
      this.generalrules = generalrules;
      if (generalrules) {
        this.updateForm(generalrules);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const generalrules = this.generalrulesFormService.getGeneralrules(this.editForm);
    if (generalrules.id !== null) {
      this.subscribeToSaveResponse(this.generalrulesService.update(generalrules));
    } else {
      this.subscribeToSaveResponse(this.generalrulesService.create(generalrules));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGeneralrules>>): void {
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

  protected updateForm(generalrules: IGeneralrules): void {
    this.generalrules = generalrules;
    this.generalrulesFormService.resetForm(this.editForm, generalrules);
  }
}
