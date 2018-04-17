export class Evenement {
    idEv : number; 
    idUser : string;
    idRasp: number; 
    date: string; 
    typeEvenement: string;
}

export class CapteurValeur{
    idCapt: number;
    labelCapt: string;
    listMesure : { "listMesure": Mesure[] };
    idRasp : number;
    constructor(){
        this.listMesure = { "listMesure": []};
    }
}

export class Mesure{
    date : string;
    donnee:number;
    idCapt:number;
}

