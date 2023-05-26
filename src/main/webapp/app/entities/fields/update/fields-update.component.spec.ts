import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FieldsFormService } from './fields-form.service';
import { FieldsService } from '../service/fields.service';
import { IFields } from '../fields.model';
import { IContestform } from 'app/entities/contestform/contestform.model';
import { ContestformService } from 'app/entities/contestform/service/contestform.service';

import { FieldsUpdateComponent } from './fields-update.component';

describe('Fields Management Update Component', () => {
  let comp: FieldsUpdateComponent;
  let fixture: ComponentFixture<FieldsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fieldsFormService: FieldsFormService;
  let fieldsService: FieldsService;
  let contestformService: ContestformService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FieldsUpdateComponent],
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
      .overrideTemplate(FieldsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FieldsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fieldsFormService = TestBed.inject(FieldsFormService);
    fieldsService = TestBed.inject(FieldsService);
    contestformService = TestBed.inject(ContestformService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contestform query and add missing value', () => {
      const fields: IFields = { id: 'CBA' };
      const contestform: IContestform = { id: '4f0caa2c-f8e5-4bf2-8daa-fcbab55af5a1' };
      fields.contestform = contestform;

      const contestformCollection: IContestform[] = [{ id: '1087ae56-7104-48d2-8a2c-ab0fdf4ca0d7' }];
      jest.spyOn(contestformService, 'query').mockReturnValue(of(new HttpResponse({ body: contestformCollection })));
      const additionalContestforms = [contestform];
      const expectedCollection: IContestform[] = [...additionalContestforms, ...contestformCollection];
      jest.spyOn(contestformService, 'addContestformToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fields });
      comp.ngOnInit();

      expect(contestformService.query).toHaveBeenCalled();
      expect(contestformService.addContestformToCollectionIfMissing).toHaveBeenCalledWith(
        contestformCollection,
        ...additionalContestforms.map(expect.objectContaining)
      );
      expect(comp.contestformsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fields: IFields = { id: 'CBA' };
      const contestform: IContestform = { id: '9ac73c13-d53f-4616-a23a-94b2b74c55bd' };
      fields.contestform = contestform;

      activatedRoute.data = of({ fields });
      comp.ngOnInit();

      expect(comp.contestformsSharedCollection).toContain(contestform);
      expect(comp.fields).toEqual(fields);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFields>>();
      const fields = { id: 'ABC' };
      jest.spyOn(fieldsFormService, 'getFields').mockReturnValue(fields);
      jest.spyOn(fieldsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fields });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fields }));
      saveSubject.complete();

      // THEN
      expect(fieldsFormService.getFields).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fieldsService.update).toHaveBeenCalledWith(expect.objectContaining(fields));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFields>>();
      const fields = { id: 'ABC' };
      jest.spyOn(fieldsFormService, 'getFields').mockReturnValue({ id: null });
      jest.spyOn(fieldsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fields: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fields }));
      saveSubject.complete();

      // THEN
      expect(fieldsFormService.getFields).toHaveBeenCalled();
      expect(fieldsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFields>>();
      const fields = { id: 'ABC' };
      jest.spyOn(fieldsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fields });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fieldsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareContestform', () => {
      it('Should forward to contestformService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(contestformService, 'compareContestform');
        comp.compareContestform(entity, entity2);
        expect(contestformService.compareContestform).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
