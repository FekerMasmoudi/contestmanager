import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContestfield } from '../contestfield.model';

@Component({
  selector: 'jhi-contestfield-detail',
  templateUrl: './contestfield-detail.component.html',
})
export class ContestfieldDetailComponent implements OnInit {
  contestfield: IContestfield | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contestfield }) => {
      this.contestfield = contestfield;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
