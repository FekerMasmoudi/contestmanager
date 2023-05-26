import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../generalrules.test-samples';

import { GeneralrulesFormService } from './generalrules-form.service';

describe('Generalrules Form Service', () => {
  let service: GeneralrulesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GeneralrulesFormService);
  });

  describe('Service methods', () => {
    describe('createGeneralrulesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGeneralrulesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            designation: expect.any(Object),
            effectdate: expect.any(Object),
          })
        );
      });

      it('passing IGeneralrules should create a new form with FormGroup', () => {
        const formGroup = service.createGeneralrulesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            designation: expect.any(Object),
            effectdate: expect.any(Object),
          })
        );
      });
    });

    describe('getGeneralrules', () => {
      it('should return NewGeneralrules for default Generalrules initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createGeneralrulesFormGroup(sampleWithNewData);

        const generalrules = service.getGeneralrules(formGroup) as any;

        expect(generalrules).toMatchObject(sampleWithNewData);
      });

      it('should return NewGeneralrules for empty Generalrules initial value', () => {
        const formGroup = service.createGeneralrulesFormGroup();

        const generalrules = service.getGeneralrules(formGroup) as any;

        expect(generalrules).toMatchObject({});
      });

      it('should return IGeneralrules', () => {
        const formGroup = service.createGeneralrulesFormGroup(sampleWithRequiredData);

        const generalrules = service.getGeneralrules(formGroup) as any;

        expect(generalrules).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGeneralrules should not enable id FormControl', () => {
        const formGroup = service.createGeneralrulesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGeneralrules should disable id FormControl', () => {
        const formGroup = service.createGeneralrulesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
