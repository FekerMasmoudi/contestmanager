import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ContestFormService, ContestFormGroup } from './contest-form.service';
import { IContest } from '../contest.model';
import { ContestService } from '../service/contest.service';
import { IContestannounce } from 'app/entities/contestannounce/contestannounce.model';
import { ContestannounceService } from 'app/entities/contestannounce/service/contestannounce.service';
import { IGrade } from 'app/entities/grade/grade.model';
import { GradeService } from 'app/entities/grade/service/grade.service';
import { ISpeciality } from 'app/entities/speciality/speciality.model';
import { SpecialityService } from 'app/entities/speciality/service/speciality.service';
import { ISector } from 'app/entities/sector/sector.model';
import { SectorService } from 'app/entities/sector/service/sector.service';

@Component({
  selector: 'jhi-contest-update',
  templateUrl: './contest-update.component.html',
})
export class ContestUpdateComponent implements OnInit {
  isSaving = false;
  contest: IContest | null = null;

  contestannouncesSharedCollection: IContestannounce[] = [];
  gradesSharedCollection: IGrade[] = [];
  specialitiesSharedCollection: ISpeciality[] = [];
  sectorsSharedCollection: ISector[] = [];

  editForm: ContestFormGroup = this.contestFormService.createContestFormGroup();

  constructor(
    protected contestService: ContestService,
    protected contestFormService: ContestFormService,
    protected contestannounceService: ContestannounceService,
    protected gradeService: GradeService,
    protected specialityService: SpecialityService,
    protected sectorService: SectorService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareContestannounce = (o1: IContestannounce | null, o2: IContestannounce | null): boolean =>
    this.contestannounceService.compareContestannounce(o1, o2);

  compareGrade = (o1: IGrade | null, o2: IGrade | null): boolean => this.gradeService.compareGrade(o1, o2);

  compareSpeciality = (o1: ISpeciality | null, o2: ISpeciality | null): boolean => this.specialityService.compareSpeciality(o1, o2);

  compareSector = (o1: ISector | null, o2: ISector | null): boolean => this.sectorService.compareSector(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contest }) => {
      this.contest = contest;
      if (contest) {
        this.updateForm(contest);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contest = this.contestFormService.getContest(this.editForm);
    if (contest.id !== null) {
      this.subscribeToSaveResponse(this.contestService.update(contest));
    } else {
      this.subscribeToSaveResponse(this.contestService.create(contest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContest>>): void {
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

  protected updateForm(contest: IContest): void {
    this.contest = contest;
    this.contestFormService.resetForm(this.editForm, contest);

    this.contestannouncesSharedCollection = this.contestannounceService.addContestannounceToCollectionIfMissing<IContestannounce>(
      this.contestannouncesSharedCollection,
      contest.idcontestannounce
    );
    this.gradesSharedCollection = this.gradeService.addGradeToCollectionIfMissing<IGrade>(this.gradesSharedCollection, contest.idgrade);
    this.specialitiesSharedCollection = this.specialityService.addSpecialityToCollectionIfMissing<ISpeciality>(
      this.specialitiesSharedCollection,
      contest.idspeciality
    );
    this.sectorsSharedCollection = this.sectorService.addSectorToCollectionIfMissing<ISector>(
      this.sectorsSharedCollection,
      contest.idsector
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contestannounceService
      .query()
      .pipe(map((res: HttpResponse<IContestannounce[]>) => res.body ?? []))
      .pipe(
        map((contestannounces: IContestannounce[]) =>
          this.contestannounceService.addContestannounceToCollectionIfMissing<IContestannounce>(
            contestannounces,
            this.contest?.idcontestannounce
          )
        )
      )
      .subscribe((contestannounces: IContestannounce[]) => (this.contestannouncesSharedCollection = contestannounces));

    this.gradeService
      .query()
      .pipe(map((res: HttpResponse<IGrade[]>) => res.body ?? []))
      .pipe(map((grades: IGrade[]) => this.gradeService.addGradeToCollectionIfMissing<IGrade>(grades, this.contest?.idgrade)))
      .subscribe((grades: IGrade[]) => (this.gradesSharedCollection = grades));

    this.specialityService
      .query()
      .pipe(map((res: HttpResponse<ISpeciality[]>) => res.body ?? []))
      .pipe(
        map((specialities: ISpeciality[]) =>
          this.specialityService.addSpecialityToCollectionIfMissing<ISpeciality>(specialities, this.contest?.idspeciality)
        )
      )
      .subscribe((specialities: ISpeciality[]) => (this.specialitiesSharedCollection = specialities));

    this.sectorService
      .query()
      .pipe(map((res: HttpResponse<ISector[]>) => res.body ?? []))
      .pipe(map((sectors: ISector[]) => this.sectorService.addSectorToCollectionIfMissing<ISector>(sectors, this.contest?.idsector)))
      .subscribe((sectors: ISector[]) => (this.sectorsSharedCollection = sectors));
  }
}
