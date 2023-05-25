import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../educationlevel.test-samples';

import { EducationlevelFormService } from './educationlevel-form.service';

describe('Educationlevel Form Service', () => {
  let service: EducationlevelFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EducationlevelFormService);
  });

  describe('Service methods', () => {
    describe('createEducationlevelFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEducationlevelFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            designation: expect.any(Object),
          })
        );
      });

      it('passing IEducationlevel should create a new form with FormGroup', () => {
        const formGroup = service.createEducationlevelFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            designation: expect.any(Object),
          })
        );
      });
    });

    describe('getEducationlevel', () => {
      it('should return NewEducationlevel for default Educationlevel initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEducationlevelFormGroup(sampleWithNewData);

        const educationlevel = service.getEducationlevel(formGroup) as any;

        expect(educationlevel).toMatchObject(sampleWithNewData);
      });

      it('should return NewEducationlevel for empty Educationlevel initial value', () => {
        const formGroup = service.createEducationlevelFormGroup();

        const educationlevel = service.getEducationlevel(formGroup) as any;

        expect(educationlevel).toMatchObject({});
      });

      it('should return IEducationlevel', () => {
        const formGroup = service.createEducationlevelFormGroup(sampleWithRequiredData);

        const educationlevel = service.getEducationlevel(formGroup) as any;

        expect(educationlevel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEducationlevel should not enable id FormControl', () => {
        const formGroup = service.createEducationlevelFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEducationlevel should disable id FormControl', () => {
        const formGroup = service.createEducationlevelFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
