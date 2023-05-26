import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EducationlevelFormService } from './educationlevel-form.service';
import { EducationlevelService } from '../service/educationlevel.service';
import { IEducationlevel } from '../educationlevel.model';

import { EducationlevelUpdateComponent } from './educationlevel-update.component';

describe('Educationlevel Management Update Component', () => {
  let comp: EducationlevelUpdateComponent;
  let fixture: ComponentFixture<EducationlevelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let educationlevelFormService: EducationlevelFormService;
  let educationlevelService: EducationlevelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EducationlevelUpdateComponent],
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
      .overrideTemplate(EducationlevelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EducationlevelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    educationlevelFormService = TestBed.inject(EducationlevelFormService);
    educationlevelService = TestBed.inject(EducationlevelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const educationlevel: IEducationlevel = { id: 'CBA' };

      activatedRoute.data = of({ educationlevel });
      comp.ngOnInit();

      expect(comp.educationlevel).toEqual(educationlevel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEducationlevel>>();
      const educationlevel = { id: 'ABC' };
      jest.spyOn(educationlevelFormService, 'getEducationlevel').mockReturnValue(educationlevel);
      jest.spyOn(educationlevelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ educationlevel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: educationlevel }));
      saveSubject.complete();

      // THEN
      expect(educationlevelFormService.getEducationlevel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(educationlevelService.update).toHaveBeenCalledWith(expect.objectContaining(educationlevel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEducationlevel>>();
      const educationlevel = { id: 'ABC' };
      jest.spyOn(educationlevelFormService, 'getEducationlevel').mockReturnValue({ id: null });
      jest.spyOn(educationlevelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ educationlevel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: educationlevel }));
      saveSubject.complete();

      // THEN
      expect(educationlevelFormService.getEducationlevel).toHaveBeenCalled();
      expect(educationlevelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEducationlevel>>();
      const educationlevel = { id: 'ABC' };
      jest.spyOn(educationlevelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ educationlevel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(educationlevelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
