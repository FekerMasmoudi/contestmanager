import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGeneralrules, NewGeneralrules } from '../generalrules.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGeneralrules for edit and NewGeneralrulesFormGroupInput for create.
 */
type GeneralrulesFormGroupInput = IGeneralrules | PartialWithRequiredKeyOf<NewGeneralrules>;

type GeneralrulesFormDefaults = Pick<NewGeneralrules, 'id'>;

type GeneralrulesFormGroupContent = {
  id: FormControl<IGeneralrules['id'] | NewGeneralrules['id']>;
  designation: FormControl<IGeneralrules['designation']>;
  effectdate: FormControl<IGeneralrules['effectdate']>;
};

export type GeneralrulesFormGroup = FormGroup<GeneralrulesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GeneralrulesFormService {
  createGeneralrulesFormGroup(generalrules: GeneralrulesFormGroupInput = { id: null }): GeneralrulesFormGroup {
    const generalrulesRawValue = {
      ...this.getFormDefaults(),
      ...generalrules,
    };
    return new FormGroup<GeneralrulesFormGroupContent>({
      id: new FormControl(
        { value: generalrulesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      designation: new FormControl(generalrulesRawValue.designation, {
        validators: [Validators.required],
      }),
      effectdate: new FormControl(generalrulesRawValue.effectdate, {
        validators: [Validators.required],
      }),
    });
  }

  getGeneralrules(form: GeneralrulesFormGroup): IGeneralrules | NewGeneralrules {
    return form.getRawValue() as IGeneralrules | NewGeneralrules;
  }

  resetForm(form: GeneralrulesFormGroup, generalrules: GeneralrulesFormGroupInput): void {
    const generalrulesRawValue = { ...this.getFormDefaults(), ...generalrules };
    form.reset(
      {
        ...generalrulesRawValue,
        id: { value: generalrulesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): GeneralrulesFormDefaults {
    return {
      id: null,
    };
  }
}
