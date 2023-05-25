import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpecialityDetailComponent } from './speciality-detail.component';

describe('Speciality Management Detail Component', () => {
  let comp: SpecialityDetailComponent;
  let fixture: ComponentFixture<SpecialityDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SpecialityDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ speciality: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(SpecialityDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SpecialityDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load speciality on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.speciality).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
