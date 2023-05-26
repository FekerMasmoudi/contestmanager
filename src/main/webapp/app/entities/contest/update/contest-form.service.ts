import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContest, NewContest } from '../contest.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContest for edit and NewContestFormGroupInput for create.
 */
type ContestFormGroupInput = IContest | PartialWithRequiredKeyOf<NewContest>;

type ContestFormDefaults = Pick<NewContest, 'id' | 'status'>;

type ContestFormGroupContent = {
  id: FormControl<IContest['id'] | NewContest['id']>;
  name: FormControl<IContest['name']>;
  parent: FormControl<IContest['parent']>;
  description: FormControl<IContest['description']>;
  nbpositions: FormControl<IContest['nbpositions']>;
  status: FormControl<IContest['status']>;
  idcontestannounce: FormControl<IContest['idcontestannounce']>;
  idgrade: FormControl<IContest['idgrade']>;
  idspeciality: FormControl<IContest['idspeciality']>;
  idsector: FormControl<IContest['idsector']>;
};

export type ContestFormGroup = FormGroup<ContestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContestFormService {
  createContestFormGroup(contest: ContestFormGroupInput = { id: null }): ContestFormGroup {
    const contestRawValue = {
      ...this.getFormDefaults(),
      ...contest,
    };
    return new FormGroup<ContestFormGroupContent>({
      id: new FormControl(
        { value: contestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(contestRawValue.name, {
        validators: [Validators.required],
      }),
      parent: new FormControl(contestRawValue.parent, {
        validators: [Validators.required],
      }),
      description: new FormControl(contestRawValue.description, {
        validators: [Validators.required],
      }),
      nbpositions: new FormControl(contestRawValue.nbpositions, {
        validators: [Validators.required],
      }),
      status: new FormControl(contestRawValue.status, {
        validators: [Validators.required],
      }),
      idcontestannounce: new FormControl(contestRawValue.idcontestannounce, {
        validators: [Validators.required],
      }),
      idgrade: new FormControl(contestRawValue.idgrade, {
        validators: [Validators.required],
      }),
      idspeciality: new FormControl(contestRawValue.idspeciality, {
        validators: [Validators.required],
      }),
      idsector: new FormControl(contestRawValue.idsector, {
        validators: [Validators.required],
      }),
    });
  }

  getContest(form: ContestFormGroup): IContest | NewContest {
    return form.getRawValue() as IContest | NewContest;
  }

  resetForm(form: ContestFormGroup, contest: ContestFormGroupInput): void {
    const contestRawValue = { ...this.getFormDefaults(), ...contest };
    form.reset(
      {
        ...contestRawValue,
        id: { value: contestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ContestFormDefaults {
    return {
      id: null,
      status: false,
    };
  }
}
