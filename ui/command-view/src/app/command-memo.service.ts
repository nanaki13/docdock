import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Command,Commands } from './model/Command';

@Injectable({
  providedIn: 'root'
})
export class CommandMemoService {

constructor(private http: HttpClient) { }

getCommands(): Observable<Commands> {
  return this.http.get<Commands>(environment.apiUrl);
}
save(c : Command){
  return this.http.put<Commands>(environment.apiUrl, c);
}

}
