import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http' 

import { Evenement, CapteurValeur, Mesure } from './json';

@Injectable()
export class RestserviceService {

  server = "http://localhost:8080/PTUT_PENIBILITE/";

  constructor(private http: Http) { }

  getServer(){
    return this.server;
  }

  private handleError(error: any): Promise<any> { 
    console.error('An error occurred', error);   
    return Promise.reject(error.message || error);
  }

  getEvenement(): Promise<string> {     
    return this.http.get(this.server +
      "webresources/generic/allEvenement" 
    ).toPromise().then(response =>response
      .json()).catch(this.handleError); 
  };
  
  getCapteur(): Promise<string>{
    return this.http.get(this.server +
      "webresources/generic/allCapteur" 
    ).toPromise().then(response =>response
      .json()).catch(this.handleError); 
  };

}
