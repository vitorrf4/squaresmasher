export class Sale {
	customerName: String  = "";
	movieTitle: String = "";
	quantityBought: number = 0;
	saleTotalPrice: number = 0;
	saleDateTime: Date = new Date();

	constructor(obj : Object) {
		obj && Object.assign(this, obj);
	}

}
