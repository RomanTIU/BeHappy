import {Component, OnInit, Optional} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TranslateService} from "@ngx-translate/core";
import {NzNotificationService} from "ng-zorro-antd/notification";
import {NzModalRef} from "ng-zorro-antd/modal";
import {DietService} from "../../../shared/http/diet.service";
import {Diet} from "../../../shared/models/diet/diet";
import {DietQuantity} from "../../../shared/models/diet/diet-quantity";
import {DietTotal} from "../../../shared/models/diet/diet-total";
import {SelectItem} from "../../../shared/models/select-item";

@Component({
  selector: 'app-add-edit-diet',
  templateUrl: './add-edit-diet.component.html',
  styleUrls: ['./add-edit-diet.component.scss'],
})

export class AddEditDietComponent implements OnInit {

  addEditDiet: FormGroup;
  diet: Diet = new Diet() ;
  dietQuantity: DietQuantity = new DietQuantity();
  dietTotal: DietTotal = new DietTotal();
  macronutrients: SelectItem[] = [];
  selectedMacronutrients;


  constructor(
    private fb: FormBuilder,
    public translate: TranslateService,
    private notification: NzNotificationService,
    private dietService: DietService,
    @Optional() private modalReference?: NzModalRef
  ) {
  }
  ngOnInit(): void {
    this.addEditDiet = this.fb.group({
      name: ['', Validators.required, Validators.pattern(/^\S+/)],
      recipe: ['', Validators.required],
      quantityName: ['', Validators.required],
      quantityValue: ['', Validators.required],
      totalGrams: ['', Validators.required],
      totalKcal: ['', Validators.required],
      totalMacronutrients: ['', Validators.required]
    })
    this.initMacronutrientsOptions()
  }

  cancel() {
    this.modalReference.close();


  }

  submitForm() {
    this.diet.name = this.addEditDiet.get('name').value;
    this.diet.recipe = this.addEditDiet.get('recipe').value;
    this.dietQuantity.name = this.addEditDiet.get('quantityName').value;
    this.dietQuantity.value = this.addEditDiet.get('quantityValue').value;
    this.dietTotal.grams = this.addEditDiet.get('totalGrams').value;
    this.dietTotal.kcal = this.addEditDiet.get('totalKcal').value;
    this.dietTotal.macronutrients = this.selectedMacronutrients;
    this.diet.dietQuantity.push(this.dietQuantity);
    this.diet.dietTotal = this.dietTotal;
    this.dietService.create(this.diet);

      this.addEditDiet.markAsTouched();
      this.addEditDiet.updateValueAndValidity();

      this.modalReference.close()
  }

  async initMacronutrientsOptions(): Promise<void> {
    this.macronutrients = (await this.dietService.macronutrients()).map(
      (format) => new SelectItem(format, format)
    );
  }
}
