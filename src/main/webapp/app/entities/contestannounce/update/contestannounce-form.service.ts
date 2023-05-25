import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContestannounce, NewContestannounce } from '../contestannounce.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContestannounce for edit and NewContestannounceFormGroupInput for create.
 */
type ContestannounceFormGroupInput = IContestannounce | PartialWithRequiredKeyOf<NewContestannounce>;

type ContestannounceFormDefaults = Pick<NewContestannounce, 'id'>;

type ContestannounceFormGroupContent = {
  id: FormControl<IContestannounce['id'] | NewContestannounce['id']>;
  announceText: FormControl<IContestannounce['announceText']>;
  startdate: FormControl<IContestannounce['startdate']>;
  limitdate: FormControl<IContestannounce['limitdate']>;
  idsgeneralrules: FormControl<IContestannounce['idsgeneralrules']>;
};

export type ContestannounceFormGroup = FormGroup<ContestannounceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContestannounceFormService {
  createContestannounceFormGroup(contestannounce: ContestannounceFormGroupInput = { id: null }): ContestannounceFormGroup {
    const contestannounceRawValue = {
      ...this.getFormDefaults(),
      ...contestannounce,
    };
    return new FormGroup<ContestannounceFormGroupContent>({
      id: new FormControl(
        { value: contestannounceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      announceText: new FormControl(contestannounceRawValue.announceText, {
        validators: [Validators.required],
      }),
      startdate: new FormControl(contestannounceRawValue.startdate, {
        validators: [Validators.required],
      }),
      limitdate: new FormControl(contestannounceRawValue.limitdate, {
        validators: [Validators.required],
      }),
      idsgeneralrules: new FormControl(contestannounceRawValue.idsgeneralrules, {
        validators: [Validators.required],
      }),
    });
  }

  getContestannounce(form: ContestannounceFormGroup): IContestannounce | NewContestannounce {
    return form.getRawValue() as IContestannounce | NewContestannounce;
  }

  resetForm(form: ContestannounceFormGroup, contestannounce: ContestannounceFormGroupInput): void {
    const contestannounceRawValue = { ...this.getFormDefaults(), ...contestannounce };
    form.reset(
      {
        ...contestannounceRawValue,
        id: { value: contestannounceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ContestannounceFormDefaults {
    return {
      id: null,
    };
  }
}
