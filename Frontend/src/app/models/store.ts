import {Movie} from "./movie";

export class Store {
  name: string = "";
  totalRevenue = 0;
  copiesTotal = 0;
  moviesInStock : Movie[] = [];
}
