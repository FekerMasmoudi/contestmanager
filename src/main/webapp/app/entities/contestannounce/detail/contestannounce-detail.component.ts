import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContestannounce } from '../contestannounce.model';

@Component({
  selector: 'jhi-contestannounce-detail',
  templateUrl: './contestannounce-detail.component.html',
})
export class ContestannounceDetailComponent implements OnInit {
  contestannounce: IContestannounce | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contestannounce }) => {
      this.contestannounce = contestannounce;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
