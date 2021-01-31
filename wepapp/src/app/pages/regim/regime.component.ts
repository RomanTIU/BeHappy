import {Component, OnInit} from "@angular/core";
import {Regime} from "../../shared/models/regime";
import {TranslateService} from '@ngx-translate/core'
import {NzSafeAny} from "ng-zorro-antd/core/types";
import {RegimeService} from "../../shared/http/regime.service";
import {Diet} from "../../shared/models/diet/diet";
import {DietQuantity} from "../../shared/models/diet/diet-quantity";
import {DietTotal} from "../../shared/models/diet/diet-total";
import {NzModalService} from "ng-zorro-antd/modal";
import {AddEditRegimeComponent} from "./add-edit-regime/add-edit-regime.component";

@Component({
  selector: 'app-regime',
  templateUrl: './regime.component.html',
  styleUrls: ['./regime.component.scss']
})

export class RegimeComponent implements OnInit {

  regimes: Regime[] = [];
  pageIndex = 1;
  pageSize = 50;
  total = 1;
  sortOrder: string | null = null;
  sortField: string | null = null;

  constructor(
    public translate :  TranslateService,
    private regimeService: RegimeService,
    private nzModalService: NzModalService
  ) {
    this.regimeService.getPage(this.pageIndex, this.pageSize, this.sortField, this.sortOrder)
      .then(data => {
        data.forEach((d) => {
          console.log(d)
          this.regimes.push(d);
        })
      })
  }

  ngOnInit(): void {
    console.log("Download")
    console.log(this.regimes.length)
  }

  showAddEditRegimeModal() {

  }

  add(): void {
    this.createAddEditUriModal();
  }


  async createAddEditUriModal(): Promise<void> {
    this.nzModalService.create({
      nzTitle: 'Add regime',
      nzContent: AddEditRegimeComponent,
      nzComponentParams: { },
      nzFooter: null,
      nzStyle: { width: '800px' },
      nzMask: false,
    });
  }

  getRegime() {

  }

  sort($event: { key: NzSafeAny; value: string | null }) {

  }
}
