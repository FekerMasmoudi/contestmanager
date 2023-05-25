import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContestannounceDetailComponent } from './contestannounce-detail.component';

describe('Contestannounce Management Detail Component', () => {
  let comp: ContestannounceDetailComponent;
  let fixture: ComponentFixture<ContestannounceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContestannounceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ contestannounce: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ContestannounceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ContestannounceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load contestannounce on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.contestannounce).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
