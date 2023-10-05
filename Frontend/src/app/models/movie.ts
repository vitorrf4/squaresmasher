export class Movie {
  movieTitle : string = "";
  releaseYear : number = 0;
  unitPrice : number = 0;
  copiesAmount : number = 0;
  posterUrl : string = "";

  constructor(obj : Object) {
    obj && Object.assign(this, obj);
  }
}
