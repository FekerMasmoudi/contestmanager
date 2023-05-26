import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEducationlevel, NewEducationlevel } from '../educationlevel.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEducationlevel for edit and NewEducationlevelFormGroupInput for create.
 */
type EducationlevelFormGroupInput = IEducationlevel | PartialWithRequiredKeyOf<NewEducationlevel>;

type EducationlevelFormDefaults = Pick<NewEducationlevel, 'id'>;

type EducationlevelFormGroupContent = {
  id: FormControl<IEducationlevel['id'] | NewEducationlevel['id']>;
  designation: FormControl<IEducationlevel['designation']>;
};

export type EducationlevelFormGroup = FormGroup<EducationlevelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EducationlevelFormService {
  createEducationlevelFormGroup(educationlevel: EducationlevelFormGroupInput = { id: null }): EducationlevelFormGroup {
    const educationlevelRawValue = {
      ...this.getFormDefaults(),
      ...educationlevel,
    };
    return new FormGroup<EducationlevelFormGroupContent>({
      id: new FormControl(
        { value: educationlevelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      designation: new FormControl(educationlevelRawValue.designation, {
        validators: [Validators.required],
      }),
    });
  }

  getEducationlevel(form: EducationlevelFormGroup): IEducationlevel | NewEducationlevel {
    return form.getRawValue() as IEducationlevel | NewEducationlevel;
  }

  resetForm(form: EducationlevelFormGroup, educationlevel: EducationlevelFormGroupInput): void {
    const educationlevelRawValue = { ...this.getFormDefaults(), ...educationlevel };
    form.reset(
      {
        ...educationlevelRawValue,
        id: { value: educationlevelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EducationlevelFormDefaults {
    return {
      id: null,
    };
  }
}
