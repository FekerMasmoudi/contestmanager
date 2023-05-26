import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GeneralrulesFormService } from './generalrules-form.service';
import { GeneralrulesService } from '../service/generalrules.service';
import { IGeneralrules } from '../generalrules.model';

import { GeneralrulesUpdateComponent } from './generalrules-update.component';

describe('Generalrules Management Update Component', () => {
  let comp: GeneralrulesUpdateComponent;
  let fixture: ComponentFixture<GeneralrulesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let generalrulesFormService: GeneralrulesFormService;
  let generalrulesService: GeneralrulesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GeneralrulesUpdateComponent],
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
      .overrideTemplate(GeneralrulesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GeneralrulesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    generalrulesFormService = TestBed.inject(GeneralrulesFormService);
    generalrulesService = TestBed.inject(GeneralrulesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const generalrules: IGeneralrules = { id: 'CBA' };

      activatedRoute.data = of({ generalrules });
      comp.ngOnInit();

      expect(comp.generalrules).toEqual(generalrules);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGeneralrules>>();
      const generalrules = { id: 'ABC' };
      jest.spyOn(generalrulesFormService, 'getGeneralrules').mockReturnValue(generalrules);
      jest.spyOn(generalrulesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ generalrules });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: generalrules }));
      saveSubject.complete();

      // THEN
      expect(generalrulesFormService.getGeneralrules).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(generalrulesService.update).toHaveBeenCalledWith(expect.objectContaining(generalrules));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGeneralrules>>();
      const generalrules = { id: 'ABC' };
      jest.spyOn(generalrulesFormService, 'getGeneralrules').mockReturnValue({ id: null });
      jest.spyOn(generalrulesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ generalrules: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: generalrules }));
      saveSubject.complete();

      // THEN
      expect(generalrulesFormService.getGeneralrules).toHaveBeenCalled();
      expect(generalrulesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGeneralrules>>();
      const generalrules = { id: 'ABC' };
      jest.spyOn(generalrulesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ generalrules });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(generalrulesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
