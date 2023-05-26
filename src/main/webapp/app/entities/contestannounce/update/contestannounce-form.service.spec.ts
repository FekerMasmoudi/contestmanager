import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../contestannounce.test-samples';

import { ContestannounceFormService } from './contestannounce-form.service';

describe('Contestannounce Form Service', () => {
  let service: ContestannounceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContestannounceFormService);
  });

  describe('Service methods', () => {
    describe('createContestannounceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContestannounceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            announceText: expect.any(Object),
            startdate: expect.any(Object),
            limitdate: expect.any(Object),
            status: expect.any(Object),
            idsgeneralrules: expect.any(Object),
          })
        );
      });

      it('passing IContestannounce should create a new form with FormGroup', () => {
        const formGroup = service.createContestannounceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            announceText: expect.any(Object),
            startdate: expect.any(Object),
            limitdate: expect.any(Object),
            status: expect.any(Object),
            idsgeneralrules: expect.any(Object),
          })
        );
      });
    });

    describe('getContestannounce', () => {
      it('should return NewContestannounce for default Contestannounce initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createContestannounceFormGroup(sampleWithNewData);

        const contestannounce = service.getContestannounce(formGroup) as any;

        expect(contestannounce).toMatchObject(sampleWithNewData);
      });

      it('should return NewContestannounce for empty Contestannounce initial value', () => {
        const formGroup = service.createContestannounceFormGroup();

        const contestannounce = service.getContestannounce(formGroup) as any;

        expect(contestannounce).toMatchObject({});
      });

      it('should return IContestannounce', () => {
        const formGroup = service.createContestannounceFormGroup(sampleWithRequiredData);

        const contestannounce = service.getContestannounce(formGroup) as any;

        expect(contestannounce).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContestannounce should not enable id FormControl', () => {
        const formGroup = service.createContestannounceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContestannounce should disable id FormControl', () => {
        const formGroup = service.createContestannounceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
