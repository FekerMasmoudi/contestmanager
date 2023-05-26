import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContestfieldDetailComponent } from './contestfield-detail.component';

describe('Contestfield Management Detail Component', () => {
  let comp: ContestfieldDetailComponent;
  let fixture: ComponentFixture<ContestfieldDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContestfieldDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ contestfield: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ContestfieldDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ContestfieldDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load contestfield on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.contestfield).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
