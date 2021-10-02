import {Injectable} from '@angular/core';
import {Observable, throwError} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Location} from "./station.model";
import {catchError, map} from "rxjs/operators";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class StationService {

  constructor(
    private http: HttpClient,
  ) {
  }


  fetchLocations(): Observable<Location[]> {

    return this.http.get<Location[]>(`https://api.mrcmekong.org/v1/time-series/location`, {
      headers: {'X-API-Key': environment.apiKey}
    }).pipe(
      catchError(() =>
        throwError('error')
      ),
      map((response: any) => {
        return response;
      })
    );

  }
}
