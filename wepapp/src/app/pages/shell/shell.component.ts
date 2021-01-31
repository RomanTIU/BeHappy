import {Component, OnInit} from "@angular/core";

@Component({
  selector: 'app-shell',
  templateUrl: './shell.component.html',
  styleUrls: ['./shell.component.scss']
})
export class ShellComponent implements OnInit {

  serverTime: string;
  activeCount: number;
  isCollapsed = false;
  isLoading: boolean = false;

  ngOnInit(): void {
  }
}
