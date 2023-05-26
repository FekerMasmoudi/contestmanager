import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ContestfieldFormService, ContestfieldFormGroup } from './contestfield-form.service';
import { IContestfield } from '../contestfield.model';
import { ContestfieldService } from '../service/contestfield.service';
import { IContest } from 'app/entities/contest/contest.model';
import { ContestService } from 'app/entities/contest/service/contest.service';

@Component({
  selector: 'jhi-contestfield-update',
  templateUrl: './contestfield-update.component.html',
})
export class ContestfieldUpdateComponent implements OnInit {
  isSaving = false;
  contestfield: IContestfield | null = null;

  contestsSharedCollection: IContest[] = [];

  editForm: ContestfieldFormGroup = this.contestfieldFormService.createContestfieldFormGroup();

  constructor(
    protected contestfieldService: ContestfieldService,
    protected contestfieldFormService: ContestfieldFormService,
    protected contestService: ContestService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareContest = (o1: IContest | null, o2: IContest | null): boolean => this.contestService.compareContest(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contestfield }) => {
      this.contestfield = contestfield;
      if (contestfield) {
        this.updateForm(contestfield);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contestfield = this.contestfieldFormService.getContestfield(this.editForm);
    if (contestfield.id !== null) {
      this.subscribeToSaveResponse(this.contestfieldService.update(contestfield));
    } else {
      this.subscribeToSaveResponse(this.contestfieldService.create(contestfield));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContestfield>>): void {
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

  protected updateForm(contestfield: IContestfield): void {
    this.contestfield = contestfield;
    this.contestfieldFormService.resetForm(this.editForm, contestfield);

    this.contestsSharedCollection = this.contestService.addContestToCollectionIfMissing<IContest>(
      this.contestsSharedCollection,
      contestfield.idcontest
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contestService
      .query()
      .pipe(map((res: HttpResponse<IContest[]>) => res.body ?? []))
      .pipe(
        map((contests: IContest[]) => this.contestService.addContestToCollectionIfMissing<IContest>(contests, this.contestfield?.idcontest))
      )
      .subscribe((contests: IContest[]) => (this.contestsSharedCollection = contests));
  }
}
