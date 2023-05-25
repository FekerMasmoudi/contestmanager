import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ContestannounceFormService, ContestannounceFormGroup } from './contestannounce-form.service';
import { IContestannounce } from '../contestannounce.model';
import { ContestannounceService } from '../service/contestannounce.service';
import { IGeneralrules } from 'app/entities/generalrules/generalrules.model';
import { GeneralrulesService } from 'app/entities/generalrules/service/generalrules.service';

@Component({
  selector: 'jhi-contestannounce-update',
  templateUrl: './contestannounce-update.component.html',
})
export class ContestannounceUpdateComponent implements OnInit {
  isSaving = false;
  contestannounce: IContestannounce | null = null;

  generalrulesSharedCollection: IGeneralrules[] = [];

  editForm: ContestannounceFormGroup = this.contestannounceFormService.createContestannounceFormGroup();

  constructor(
    protected contestannounceService: ContestannounceService,
    protected contestannounceFormService: ContestannounceFormService,
    protected generalrulesService: GeneralrulesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareGeneralrules = (o1: IGeneralrules | null, o2: IGeneralrules | null): boolean =>
    this.generalrulesService.compareGeneralrules(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contestannounce }) => {
      this.contestannounce = contestannounce;
      if (contestannounce) {
        this.updateForm(contestannounce);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contestannounce = this.contestannounceFormService.getContestannounce(this.editForm);
    if (contestannounce.id !== null) {
      this.subscribeToSaveResponse(this.contestannounceService.update(contestannounce));
    } else {
      this.subscribeToSaveResponse(this.contestannounceService.create(contestannounce));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContestannounce>>): void {
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

  protected updateForm(contestannounce: IContestannounce): void {
    this.contestannounce = contestannounce;
    this.contestannounceFormService.resetForm(this.editForm, contestannounce);

    this.generalrulesSharedCollection = this.generalrulesService.addGeneralrulesToCollectionIfMissing<IGeneralrules>(
      this.generalrulesSharedCollection,
      contestannounce.idsgeneralrules
    );
  }

  protected loadRelationshipsOptions(): void {
    this.generalrulesService
      .query()
      .pipe(map((res: HttpResponse<IGeneralrules[]>) => res.body ?? []))
      .pipe(
        map((generalrules: IGeneralrules[]) =>
          this.generalrulesService.addGeneralrulesToCollectionIfMissing<IGeneralrules>(generalrules, this.contestannounce?.idsgeneralrules)
        )
      )
      .subscribe((generalrules: IGeneralrules[]) => (this.generalrulesSharedCollection = generalrules));
  }
}
