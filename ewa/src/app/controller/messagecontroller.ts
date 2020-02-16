import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, Observer} from 'rxjs';
import {SessionService} from '../services/session.service';


@Injectable({
  providedIn: 'root'
})

/**
 * IMPORTANT. DONT CHANGE FILE NAME(INCLUDING UPPERCASE/LOWERCASE) OR ELSE IT WILL BE HIT BY A BUG WHICH
 * WILL DESTROY THE HUMAN KIND(and will break the file type).
 */
export class MessageController {

  constructor(private http: HttpClient,
              private serviceSession: SessionService) {
  }

  getMessage(): Observable<Object>{
    return this.http.get("/api/message");
  }

  saveMessage(title: string, message: string,
              longitude: number, latitude: number ) {
    let body: any = {
      "title" : title,
      "message" : message,
      "latitude" : latitude,
      "longitude" : longitude,
      "message_Created" : Date.now()
    };

    this.http.post('/api/message/' + this.serviceSession.token, body).subscribe(
      (data: any) => {
        console.log(data);
      }
    );
  }

  updateMessage(message: any) {
    return this.http.put("/api/message/"+this.serviceSession.token, message);
  }
}
