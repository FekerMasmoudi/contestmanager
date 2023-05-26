import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FieldsFormService, FieldsFormGroup } from './fields-form.service';
import { IFields } from '../fields.model';
import { FieldsService } from '../service/fields.service';
import { IContestform } from 'app/entities/contestform/contestform.model';
import { ContestformService } from 'app/entities/contestform/service/contestform.service';
import { etype } from 'app/entities/enumerations/etype.model';

@Component({
  selector: 'jhi-fields-update',
  templateUrl: './fields-update.component.html',
})
export class FieldsUpdateComponent implements OnInit {
  isSaving = false;
  fields: IFields | null = null;
  etypeValues = Object.keys(etype);

  contestformsSharedCollection: IContestform[] = [];

  editForm: FieldsFormGroup = this.fieldsFormService.createFieldsFormGroup();

  constructor(
    protected fieldsService: FieldsService,
    protected fieldsFormService: FieldsFormService,
    protected contestformService: ContestformService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareContestform = (o1: IContestform | null, o2: IContestform | null): boolean => this.contestformService.compareContestform(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fields }) => {
      this.fields = fields;
      if (fields) {
        this.updateForm(fields);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fields = this.fieldsFormService.getFields(this.editForm);
    if (fields.id !== null) {
      this.subscribeToSaveResponse(this.fieldsService.update(fields));
    } else {
      this.subscribeToSaveResponse(this.fieldsService.create(fields));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFields>>): void {
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

  protected updateForm(fields: IFields): void {
    this.fields = fields;
    this.fieldsFormService.resetForm(this.editForm, fields);

    this.contestformsSharedCollection = this.contestformService.addContestformToCollectionIfMissing<IContestform>(
      this.contestformsSharedCollection,
      fields.contestform
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contestformService
      .query()
      .pipe(map((res: HttpResponse<IContestform[]>) => res.body ?? []))
      .pipe(
        map((contestforms: IContestform[]) =>
          this.contestformService.addContestformToCollectionIfMissing<IContestform>(contestforms, this.fields?.contestform)
        )
      )
      .subscribe((contestforms: IContestform[]) => (this.contestformsSharedCollection = contestforms));
  }
}
