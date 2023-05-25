import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContestannounceFormService } from './contestannounce-form.service';
import { ContestannounceService } from '../service/contestannounce.service';
import { IContestannounce } from '../contestannounce.model';
import { IGeneralrules } from 'app/entities/generalrules/generalrules.model';
import { GeneralrulesService } from 'app/entities/generalrules/service/generalrules.service';

import { ContestannounceUpdateComponent } from './contestannounce-update.component';

describe('Contestannounce Management Update Component', () => {
  let comp: ContestannounceUpdateComponent;
  let fixture: ComponentFixture<ContestannounceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contestannounceFormService: ContestannounceFormService;
  let contestannounceService: ContestannounceService;
  let generalrulesService: GeneralrulesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ContestannounceUpdateComponent],
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
      .overrideTemplate(ContestannounceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContestannounceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contestannounceFormService = TestBed.inject(ContestannounceFormService);
    contestannounceService = TestBed.inject(ContestannounceService);
    generalrulesService = TestBed.inject(GeneralrulesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Generalrules query and add missing value', () => {
      const contestannounce: IContestannounce = { id: 'CBA' };
      const idsgeneralrules: IGeneralrules = { id: '6c99d282-ecec-4404-be9f-f42feb09d085' };
      contestannounce.idsgeneralrules = idsgeneralrules;

      const generalrulesCollection: IGeneralrules[] = [{ id: '41d98023-f282-4c61-b735-bc38a0e04419' }];
      jest.spyOn(generalrulesService, 'query').mockReturnValue(of(new HttpResponse({ body: generalrulesCollection })));
      const additionalGeneralrules = [idsgeneralrules];
      const expectedCollection: IGeneralrules[] = [...additionalGeneralrules, ...generalrulesCollection];
      jest.spyOn(generalrulesService, 'addGeneralrulesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contestannounce });
      comp.ngOnInit();

      expect(generalrulesService.query).toHaveBeenCalled();
      expect(generalrulesService.addGeneralrulesToCollectionIfMissing).toHaveBeenCalledWith(
        generalrulesCollection,
        ...additionalGeneralrules.map(expect.objectContaining)
      );
      expect(comp.generalrulesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contestannounce: IContestannounce = { id: 'CBA' };
      const idsgeneralrules: IGeneralrules = { id: '05fb6343-9f12-49cd-9d13-31cab968d401' };
      contestannounce.idsgeneralrules = idsgeneralrules;

      activatedRoute.data = of({ contestannounce });
      comp.ngOnInit();

      expect(comp.generalrulesSharedCollection).toContain(idsgeneralrules);
      expect(comp.contestannounce).toEqual(contestannounce);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContestannounce>>();
      const contestannounce = { id: 'ABC' };
      jest.spyOn(contestannounceFormService, 'getContestannounce').mockReturnValue(contestannounce);
      jest.spyOn(contestannounceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contestannounce });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contestannounce }));
      saveSubject.complete();

      // THEN
      expect(contestannounceFormService.getContestannounce).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contestannounceService.update).toHaveBeenCalledWith(expect.objectContaining(contestannounce));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContestannounce>>();
      const contestannounce = { id: 'ABC' };
      jest.spyOn(contestannounceFormService, 'getContestannounce').mockReturnValue({ id: null });
      jest.spyOn(contestannounceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contestannounce: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contestannounce }));
      saveSubject.complete();

      // THEN
      expect(contestannounceFormService.getContestannounce).toHaveBeenCalled();
      expect(contestannounceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContestannounce>>();
      const contestannounce = { id: 'ABC' };
      jest.spyOn(contestannounceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contestannounce });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contestannounceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareGeneralrules', () => {
      it('Should forward to generalrulesService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(generalrulesService, 'compareGeneralrules');
        comp.compareGeneralrules(entity, entity2);
        expect(generalrulesService.compareGeneralrules).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
