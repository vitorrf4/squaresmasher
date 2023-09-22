export class User {
    id: number = -1;
    name: string = "";

    constructor(obj?: Object) {
      obj && Object.assign(this, obj);
    }
}
