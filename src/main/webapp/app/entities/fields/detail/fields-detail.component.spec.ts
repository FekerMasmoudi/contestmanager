import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FieldsDetailComponent } from './fields-detail.component';

describe('Fields Management Detail Component', () => {
  let comp: FieldsDetailComponent;
  let fixture: ComponentFixture<FieldsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FieldsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fields: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(FieldsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FieldsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fields on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fields).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
