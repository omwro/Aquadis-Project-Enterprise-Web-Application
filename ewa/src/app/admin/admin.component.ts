import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  adminTitle: String;
  private _accounts: number;
  messages: number;
  public users: String[];
  private status: number;
  private messagesData: String[];
  private state: number;
  private mapData: String[];

  constructor(private http: HttpClient) {
  }

  get accounts(): number {
    return this._accounts;
  }

  ngOnInit() {
    this.adminTitle = "Admin dashboard";
    this.getData();
    this.state = 0;
  }

  toggleState(n: number) {
    this.state = n;
  }

  getStatusText(n: number) {
    if (n == 0) {
      return 'Enabled';
    }
    return 'Disabled';
  }

  getData() {
    this.http.get('/api/adminUsers').subscribe(
      (result: any) => {
        this._accounts = result;
      },
      error => {
        console.log('Error:', error);
      }
    );

    this.http.get('/api/adminMessages').subscribe(
      (result: any) => {
        this.messages = result;
      },
      error => {
        console.log('Error:', error);
      }
    );

    this.http.get('/api/adminData').subscribe(
      (result: any) => {
        this.users = result;
      },
      error => {
        console.log('Error:', error);
      }
    );

    this.http.get('/api/messagesData').subscribe(
      (result: any) => {
        console.log(result);
        this.messagesData = result;
      },
      error => {
        console.log('Error:', error);
      }
    );

    this.http.get('/api/map').subscribe(
      (result: any) => {
        console.log(result);
        this.mapData = result;
      },
      error => {
        console.log('Error:', error);
      }
    );
  }

  disableUser(id: number) {
    this.http.get('/api/adminDisable/' + id).subscribe(
      (result: boolean) => {
        if (result) {
          alert('User is disabled!');
          this.getData();
        }
      },
      error => {
        console.log('Error:', error);
      }
    );
  }

  enableUser(id: number) {
    this.http.get('/api/adminEnable/' + id).subscribe(
      (result: boolean) => {
        if (result) {
          console.log(result);
          alert('User is enabled!');
          this.getData();
        }
      },
      error => {
        console.log('Error:', error);
      }
    );
  }

  disableMessage(id: number) {
    this.http.get('/api/messageDisable/' + id).subscribe(
      (result: boolean) => {
        if (result) {
          alert('Message is disabled');
          this.getData();
        }
      },
      error => {
        console.log('Error:', error);
      }
    );
  }

  enableMessage(id: number) {
    this.http.get('/api/messageEnable/' + id).subscribe(
      (result: boolean) => {
        if (result) {
          alert('Message is enabled');
          this.getData();
        }
      },
      error => {
        console.log('Error:', error);
      }
    );
  }

  changeNewValue(key: string, event) {
    let value = parseInt(event.srcElement.value);
    // @ts-ignore
    let object = this.mapData.filter(m => m.keyname == key);
    // @ts-ignore
    object[0].value = value;
  }

  updateMap(key: string) {
    // @ts-ignore
    let value = this.mapData.filter(m => m.keyname == key)[0].value;
    this.http.get('/api/map/update/' + key + '/' + value).subscribe(
      (result: any) => {
        console.log('UpdateMap():', result);
        this.getData();
      },
      error => {
        console.log('Error:', error);
      }
    );
  }
}
