import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGeneralrules } from '../generalrules.model';

@Component({
  selector: 'jhi-generalrules-detail',
  templateUrl: './generalrules-detail.component.html',
})
export class GeneralrulesDetailComponent implements OnInit {
  generalrules: IGeneralrules | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ generalrules }) => {
      this.generalrules = generalrules;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
