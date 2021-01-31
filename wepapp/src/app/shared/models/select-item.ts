export class SelectItem {
  label: string | number;
  value: string | number;

  constructor(label: string | number, value: string | number) {
    this.label = label;
    this.value = value;
  }
}
