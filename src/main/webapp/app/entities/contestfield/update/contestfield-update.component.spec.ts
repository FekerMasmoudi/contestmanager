import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContestfieldFormService } from './contestfield-form.service';
import { ContestfieldService } from '../service/contestfield.service';
import { IContestfield } from '../contestfield.model';
import { IContest } from 'app/entities/contest/contest.model';
import { ContestService } from 'app/entities/contest/service/contest.service';

import { ContestfieldUpdateComponent } from './contestfield-update.component';

describe('Contestfield Management Update Component', () => {
  let comp: ContestfieldUpdateComponent;
  let fixture: ComponentFixture<ContestfieldUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contestfieldFormService: ContestfieldFormService;
  let contestfieldService: ContestfieldService;
  let contestService: ContestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ContestfieldUpdateComponent],
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
      .overrideTemplate(ContestfieldUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContestfieldUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contestfieldFormService = TestBed.inject(ContestfieldFormService);
    contestfieldService = TestBed.inject(ContestfieldService);
    contestService = TestBed.inject(ContestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contest query and add missing value', () => {
      const contestfield: IContestfield = { id: 'CBA' };
      const idcontest: IContest = { id: '174c8b2e-5571-49cb-8d0c-be910e753e72' };
      contestfield.idcontest = idcontest;

      const contestCollection: IContest[] = [{ id: '935aff27-0ac0-49a9-a990-fadab31041c1' }];
      jest.spyOn(contestService, 'query').mockReturnValue(of(new HttpResponse({ body: contestCollection })));
      const additionalContests = [idcontest];
      const expectedCollection: IContest[] = [...additionalContests, ...contestCollection];
      jest.spyOn(contestService, 'addContestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contestfield });
      comp.ngOnInit();

      expect(contestService.query).toHaveBeenCalled();
      expect(contestService.addContestToCollectionIfMissing).toHaveBeenCalledWith(
        contestCollection,
        ...additionalContests.map(expect.objectContaining)
      );
      expect(comp.contestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contestfield: IContestfield = { id: 'CBA' };
      const idcontest: IContest = { id: 'fad65411-6110-4358-95b5-71718560f26e' };
      contestfield.idcontest = idcontest;

      activatedRoute.data = of({ contestfield });
      comp.ngOnInit();

      expect(comp.contestsSharedCollection).toContain(idcontest);
      expect(comp.contestfield).toEqual(contestfield);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContestfield>>();
      const contestfield = { id: 'ABC' };
      jest.spyOn(contestfieldFormService, 'getContestfield').mockReturnValue(contestfield);
      jest.spyOn(contestfieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contestfield });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contestfield }));
      saveSubject.complete();

      // THEN
      expect(contestfieldFormService.getContestfield).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contestfieldService.update).toHaveBeenCalledWith(expect.objectContaining(contestfield));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContestfield>>();
      const contestfield = { id: 'ABC' };
      jest.spyOn(contestfieldFormService, 'getContestfield').mockReturnValue({ id: null });
      jest.spyOn(contestfieldService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contestfield: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contestfield }));
      saveSubject.complete();

      // THEN
      expect(contestfieldFormService.getContestfield).toHaveBeenCalled();
      expect(contestfieldService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContestfield>>();
      const contestfield = { id: 'ABC' };
      jest.spyOn(contestfieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contestfield });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contestfieldService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareContest', () => {
      it('Should forward to contestService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(contestService, 'compareContest');
        comp.compareContest(entity, entity2);
        expect(contestService.compareContest).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
