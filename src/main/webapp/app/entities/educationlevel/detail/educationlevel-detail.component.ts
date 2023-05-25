import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEducationlevel } from '../educationlevel.model';

@Component({
  selector: 'jhi-educationlevel-detail',
  templateUrl: './educationlevel-detail.component.html',
})
export class EducationlevelDetailComponent implements OnInit {
  educationlevel: IEducationlevel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ educationlevel }) => {
      this.educationlevel = educationlevel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
