import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../contestfield.test-samples';

import { ContestfieldFormService } from './contestfield-form.service';

describe('Contestfield Form Service', () => {
  let service: ContestfieldFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContestfieldFormService);
  });

  describe('Service methods', () => {
    describe('createContestfieldFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContestfieldFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            reference: expect.any(Object),
            mandatory: expect.any(Object),
            fopconstraint: expect.any(Object),
            fvalue: expect.any(Object),
            sopconstraint: expect.any(Object),
            svalue: expect.any(Object),
            idcontest: expect.any(Object),
          })
        );
      });

      it('passing IContestfield should create a new form with FormGroup', () => {
        const formGroup = service.createContestfieldFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            reference: expect.any(Object),
            mandatory: expect.any(Object),
            fopconstraint: expect.any(Object),
            fvalue: expect.any(Object),
            sopconstraint: expect.any(Object),
            svalue: expect.any(Object),
            idcontest: expect.any(Object),
          })
        );
      });
    });

    describe('getContestfield', () => {
      it('should return NewContestfield for default Contestfield initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createContestfieldFormGroup(sampleWithNewData);

        const contestfield = service.getContestfield(formGroup) as any;

        expect(contestfield).toMatchObject(sampleWithNewData);
      });

      it('should return NewContestfield for empty Contestfield initial value', () => {
        const formGroup = service.createContestfieldFormGroup();

        const contestfield = service.getContestfield(formGroup) as any;

        expect(contestfield).toMatchObject({});
      });

      it('should return IContestfield', () => {
        const formGroup = service.createContestfieldFormGroup(sampleWithRequiredData);

        const contestfield = service.getContestfield(formGroup) as any;

        expect(contestfield).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContestfield should not enable id FormControl', () => {
        const formGroup = service.createContestfieldFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContestfield should disable id FormControl', () => {
        const formGroup = service.createContestfieldFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
