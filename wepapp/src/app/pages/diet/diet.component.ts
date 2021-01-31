import {Component, OnDestroy, OnInit} from "@angular/core";
import {TranslateService} from "@ngx-translate/core";
import {Diet} from "../../shared/models/diet/diet";
import {DietService} from "../../shared/http/diet.service";
import {NzSafeAny} from "ng-zorro-antd/core/types";
import {NzModalService} from "ng-zorro-antd/modal";
import {AddEditDietComponent} from "./add-edit-diet/add-edit-diet.component";
import {NotificationService} from "../../shared/service/notification.service";

@Component({
  selector: 'app-diet.component',
  templateUrl: './diet.component.html',
  styleUrls: ['./diet.component.scss'],
})

export class DietComponent implements OnInit,OnDestroy {

  diets: Diet[] = [];

  pageIndex = 1;
  pageSize = 50;
  total: 1;

  constructor(
    public translate: TranslateService,
    private notification: NotificationService,
    private dietService: DietService,
    private nzModalService: NzModalService
  ) {
  }

  async ngOnInit(): Promise<void> {
   await this.getData();
   console.log(this.diets)
  }

  async getData(reset = false): Promise<void> {
    if (reset) {
      this.pageIndex = 1;
    }
    this.dietService.get().then( data => {
      data.forEach(d =>{
        this.diets.push(d)
      })
    })
  }

  sort($event: { key: NzSafeAny; value: string | null }) {

  }

  async createAddEditUriModal(): Promise<void> {
    this.nzModalService.create({
      nzTitle: 'Add diet',
      nzContent: AddEditDietComponent,
      nzComponentParams: { },
      nzFooter: null,
      nzStyle: { width: '800px' },
      nzMask: false,
    });
  }

  add() {
    this.createAddEditUriModal();
  }

  showDeleteLanguageConfirm(diet: Diet) {
    let contentMessage: string;
    this.translate.get('DIET.NOTIFICATION.DELETE.ASK', { name: diet.name }).subscribe(res =>
      contentMessage = res
    );
    this.nzModalService.confirm({
      nzTitle: this.translate.instant('GENERAL.SURE'),
      nzContent: contentMessage,
      nzOkText: this.translate.instant('GENERAL.YES'),
      nzOnOk: async () => {
        this.delete(diet.id).then(
          () => {
            let message: string;
            this.translate.get('DIET.NOTIFICATION.DELETE.SUCCESS', { name: diet.name }).subscribe(
              res => message = res
            );
            this.notification.notifySuccess(message);
          }
        )
      }
    });
  }
  async delete(id: number): Promise<void> {
    await this.dietService.delete(id);
    await this.getData(true);
  }

  ngOnDestroy(): void {
  }
}
