import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EducationlevelDetailComponent } from './educationlevel-detail.component';

describe('Educationlevel Management Detail Component', () => {
  let comp: EducationlevelDetailComponent;
  let fixture: ComponentFixture<EducationlevelDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EducationlevelDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ educationlevel: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(EducationlevelDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EducationlevelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load educationlevel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.educationlevel).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
