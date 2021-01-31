import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TranslateService} from "@ngx-translate/core";
import {RegimeService} from "../../../shared/http/regime.service";
import {NzModalService} from "ng-zorro-antd/modal";
import {NzNotificationService} from "ng-zorro-antd/notification";

@Component({
  selector: 'app-add-edit-regime',
  templateUrl: './add-edit-regime.component.html',
  styleUrls: ['./add-edit-regime.component.scss'],
})
export class AddEditRegimeComponent implements OnInit {

  addEditRegime: FormGroup;

  constructor(
    private fb: FormBuilder,
    public translate: TranslateService,
    private regimeService: RegimeService,
    private modalService: NzModalService,
    private notification: NzNotificationService
  ) {
  }
  ngOnInit(): void {
    this.addEditRegime = this.fb.group({
      name: ['', Validators.required, Validators.pattern(/^\S+/)],
      dietName: ['', Validators.required, Validators.pattern(/^\S+/)],
    })
  }




}
