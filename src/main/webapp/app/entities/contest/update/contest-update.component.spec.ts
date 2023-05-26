import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContestFormService } from './contest-form.service';
import { ContestService } from '../service/contest.service';
import { IContest } from '../contest.model';
import { IContestannounce } from 'app/entities/contestannounce/contestannounce.model';
import { ContestannounceService } from 'app/entities/contestannounce/service/contestannounce.service';
import { IGrade } from 'app/entities/grade/grade.model';
import { GradeService } from 'app/entities/grade/service/grade.service';
import { ISpeciality } from 'app/entities/speciality/speciality.model';
import { SpecialityService } from 'app/entities/speciality/service/speciality.service';
import { ISector } from 'app/entities/sector/sector.model';
import { SectorService } from 'app/entities/sector/service/sector.service';

import { ContestUpdateComponent } from './contest-update.component';

describe('Contest Management Update Component', () => {
  let comp: ContestUpdateComponent;
  let fixture: ComponentFixture<ContestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contestFormService: ContestFormService;
  let contestService: ContestService;
  let contestannounceService: ContestannounceService;
  let gradeService: GradeService;
  let specialityService: SpecialityService;
  let sectorService: SectorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ContestUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ContestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contestFormService = TestBed.inject(ContestFormService);
    contestService = TestBed.inject(ContestService);
    contestannounceService = TestBed.inject(ContestannounceService);
    gradeService = TestBed.inject(GradeService);
    specialityService = TestBed.inject(SpecialityService);
    sectorService = TestBed.inject(SectorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contestannounce query and add missing value', () => {
      const contest: IContest = { id: 'CBA' };
      const idcontestannounce: IContestannounce = { id: '91e8ff94-8e2c-4eb4-b5fd-c4f754653bf0' };
      contest.idcontestannounce = idcontestannounce;

      const contestannounceCollection: IContestannounce[] = [{ id: '3257b78f-87c2-4cfb-b6cc-fbc9d19bc778' }];
      jest.spyOn(contestannounceService, 'query').mockReturnValue(of(new HttpResponse({ body: contestannounceCollection })));
      const additionalContestannounces = [idcontestannounce];
      const expectedCollection: IContestannounce[] = [...additionalContestannounces, ...contestannounceCollection];
      jest.spyOn(contestannounceService, 'addContestannounceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      expect(contestannounceService.query).toHaveBeenCalled();
      expect(contestannounceService.addContestannounceToCollectionIfMissing).toHaveBeenCalledWith(
        contestannounceCollection,
        ...additionalContestannounces.map(expect.objectContaining)
      );
      expect(comp.contestannouncesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Grade query and add missing value', () => {
      const contest: IContest = { id: 'CBA' };
      const idgrade: IGrade = { id: '7efe2803-45a5-4ff1-ad95-aecbcb4673af' };
      contest.idgrade = idgrade;

      const gradeCollection: IGrade[] = [{ id: 'c09ddd6c-e28f-4837-bde7-fa012aafa1be' }];
      jest.spyOn(gradeService, 'query').mockReturnValue(of(new HttpResponse({ body: gradeCollection })));
      const additionalGrades = [idgrade];
      const expectedCollection: IGrade[] = [...additionalGrades, ...gradeCollection];
      jest.spyOn(gradeService, 'addGradeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      expect(gradeService.query).toHaveBeenCalled();
      expect(gradeService.addGradeToCollectionIfMissing).toHaveBeenCalledWith(
        gradeCollection,
        ...additionalGrades.map(expect.objectContaining)
      );
      expect(comp.gradesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Speciality query and add missing value', () => {
      const contest: IContest = { id: 'CBA' };
      const idspeciality: ISpeciality = { id: 'a0762d6e-f835-4366-a4d7-fde3f854920d' };
      contest.idspeciality = idspeciality;

      const specialityCollection: ISpeciality[] = [{ id: 'ab5fb971-6dba-4ede-9d1e-38f7710f3ca7' }];
      jest.spyOn(specialityService, 'query').mockReturnValue(of(new HttpResponse({ body: specialityCollection })));
      const additionalSpecialities = [idspeciality];
      const expectedCollection: ISpeciality[] = [...additionalSpecialities, ...specialityCollection];
      jest.spyOn(specialityService, 'addSpecialityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      expect(specialityService.query).toHaveBeenCalled();
      expect(specialityService.addSpecialityToCollectionIfMissing).toHaveBeenCalledWith(
        specialityCollection,
        ...additionalSpecialities.map(expect.objectContaining)
      );
      expect(comp.specialitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Sector query and add missing value', () => {
      const contest: IContest = { id: 'CBA' };
      const idsector: ISector = { id: 'c8250a99-2135-41af-9024-97f6c1313bfb' };
      contest.idsector = idsector;

      const sectorCollection: ISector[] = [{ id: '46a1f8d4-23ac-4e19-9278-9c77c764a4f5' }];
      jest.spyOn(sectorService, 'query').mockReturnValue(of(new HttpResponse({ body: sectorCollection })));
      const additionalSectors = [idsector];
      const expectedCollection: ISector[] = [...additionalSectors, ...sectorCollection];
      jest.spyOn(sectorService, 'addSectorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      expect(sectorService.query).toHaveBeenCalled();
      expect(sectorService.addSectorToCollectionIfMissing).toHaveBeenCalledWith(
        sectorCollection,
        ...additionalSectors.map(expect.objectContaining)
      );
      expect(comp.sectorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contest: IContest = { id: 'CBA' };
      const idcontestannounce: IContestannounce = { id: '41313d20-6542-46df-b257-3a5132a07d28' };
      contest.idcontestannounce = idcontestannounce;
      const idgrade: IGrade = { id: '8dec9910-6548-4822-9392-6cf6bba52a16' };
      contest.idgrade = idgrade;
      const idspeciality: ISpeciality = { id: '2115d1d3-2229-4dac-8817-1174d1105b08' };
      contest.idspeciality = idspeciality;
      const idsector: ISector = { id: '9fa32bd1-f997-4f1d-b68c-7f79c0352872' };
      contest.idsector = idsector;

      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      expect(comp.contestannouncesSharedCollection).toContain(idcontestannounce);
      expect(comp.gradesSharedCollection).toContain(idgrade);
      expect(comp.specialitiesSharedCollection).toContain(idspeciality);
      expect(comp.sectorsSharedCollection).toContain(idsector);
      expect(comp.contest).toEqual(contest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContest>>();
      const contest = { id: 'ABC' };
      jest.spyOn(contestFormService, 'getContest').mockReturnValue(contest);
      jest.spyOn(contestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contest }));
      saveSubject.complete();

      // THEN
      expect(contestFormService.getContest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contestService.update).toHaveBeenCalledWith(expect.objectContaining(contest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContest>>();
      const contest = { id: 'ABC' };
      jest.spyOn(contestFormService, 'getContest').mockReturnValue({ id: null });
      jest.spyOn(contestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contest }));
      saveSubject.complete();

      // THEN
      expect(contestFormService.getContest).toHaveBeenCalled();
      expect(contestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContest>>();
      const contest = { id: 'ABC' };
      jest.spyOn(contestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareContestannounce', () => {
      it('Should forward to contestannounceService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(contestannounceService, 'compareContestannounce');
        comp.compareContestannounce(entity, entity2);
        expect(contestannounceService.compareContestannounce).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGrade', () => {
      it('Should forward to gradeService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(gradeService, 'compareGrade');
        comp.compareGrade(entity, entity2);
        expect(gradeService.compareGrade).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSpeciality', () => {
      it('Should forward to specialityService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(specialityService, 'compareSpeciality');
        comp.compareSpeciality(entity, entity2);
        expect(specialityService.compareSpeciality).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSector', () => {
      it('Should forward to sectorService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(sectorService, 'compareSector');
        comp.compareSector(entity, entity2);
        expect(sectorService.compareSector).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
