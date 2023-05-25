import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GeneralrulesDetailComponent } from './generalrules-detail.component';

describe('Generalrules Management Detail Component', () => {
  let comp: GeneralrulesDetailComponent;
  let fixture: ComponentFixture<GeneralrulesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GeneralrulesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ generalrules: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(GeneralrulesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GeneralrulesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load generalrules on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.generalrules).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
