import { Component } from '@angular/core';
import { RestserviceService } from './restservice.service';

import { Evenement, CapteurValeur, Mesure } from './json'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Projet Penibilite';
  server: string;
  jsonEvenement: Evenement[];
  ev : string;
  listUser: string[] = [];
  selecteduser:string;
  listRasp: number[] = [];
  selectedrasp: number;
  listDate : string[] = [];
  selectedate: string;
  capt : string;
  jsonCapt: CapteurValeur[];
  listCapt: string[] = [];
  selectedcapt : string;


  constructor(private service:RestserviceService){
    this.server = service.getServer();
    service.getEvenement().then(
      evenement => {
        //console.log(evenement);

        this.ev = evenement;
        //console.log(this.ev);

        this.jsonEvenement = JSON.parse(this.ev);

        for( let event of this.jsonEvenement){

          let x = this.listUser.find( x => x == event.idUser);
          if(x == null){
            this.listUser.push(event.idUser);
          }

          let y = this.listRasp.find( y => y == event.idRasp);
          if(y == null){
            this.listRasp.push(event.idRasp);
          }
          
          //console.log(event);

          let toArray =  event.date.split(" ");
          let z = this.listDate.find(z => z == toArray[0]);
          if(y == null){
            this.listDate.push(toArray[0]);
          }     
        }
        
        //console.log(this.listDate);

      });

      service.getCapteur().then(
        capteur => {
          this.capt = capteur;

          this.jsonCapt = JSON.parse(this.capt);

          //console.log(this.jsonCapt[0].listMesure[0]);

          for( let capt of this.jsonCapt){

            let x = this.listCapt.find( x => x == capt.labelCapt);
            if(x == null){
              this.listCapt.push(capt.labelCapt);
            }
          }

      });

  }
}
