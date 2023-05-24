import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFields } from '../fields.model';

@Component({
  selector: 'jhi-fields-detail',
  templateUrl: './fields-detail.component.html',
})
export class FieldsDetailComponent implements OnInit {
  fields: IFields | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fields }) => {
      this.fields = fields;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
