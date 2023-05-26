import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ContestformFormService, ContestformFormGroup } from './contestform-form.service';
import { IContestform } from '../contestform.model';
import { ContestformService } from '../service/contestform.service';
import { IContest } from 'app/entities/contest/contest.model';
import { ContestService } from 'app/entities/contest/service/contest.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-contestform-update',
  templateUrl: './contestform-update.component.html',
})
export class ContestformUpdateComponent implements OnInit {
  isSaving = false;
  contestform: IContestform | null = null;

  contestsSharedCollection: IContest[] = [];
  usersSharedCollection: IUser[] = [];

  editForm: ContestformFormGroup = this.contestformFormService.createContestformFormGroup();

  constructor(
    protected contestformService: ContestformService,
    protected contestformFormService: ContestformFormService,
    protected contestService: ContestService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareContest = (o1: IContest | null, o2: IContest | null): boolean => this.contestService.compareContest(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contestform }) => {
      this.contestform = contestform;
      if (contestform) {
        this.updateForm(contestform);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contestform = this.contestformFormService.getContestform(this.editForm);
    if (contestform.id !== null) {
      this.subscribeToSaveResponse(this.contestformService.update(contestform));
    } else {
      this.subscribeToSaveResponse(this.contestformService.create(contestform));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContestform>>): void {
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

  protected updateForm(contestform: IContestform): void {
    this.contestform = contestform;
    this.contestformFormService.resetForm(this.editForm, contestform);

    this.contestsSharedCollection = this.contestService.addContestToCollectionIfMissing<IContest>(
      this.contestsSharedCollection,
      contestform.contest
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, contestform.user);
  }

  protected loadRelationshipsOptions(): void {
    this.contestService
      .query()
      .pipe(map((res: HttpResponse<IContest[]>) => res.body ?? []))
      .pipe(
        map((contests: IContest[]) => this.contestService.addContestToCollectionIfMissing<IContest>(contests, this.contestform?.contest))
      )
      .subscribe((contests: IContest[]) => (this.contestsSharedCollection = contests));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.contestform?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
