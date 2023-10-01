export class Sale {
	customerName: String  = "";
	movieTitle: String = "";
	quantityBought: number = 0;
	salePrice: number = 0;
	saleDateTime: Date = new Date();

	constructor(obj : Object) {
		obj && Object.assign(this, obj);
	}

}
