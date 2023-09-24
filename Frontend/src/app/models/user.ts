export class User {
    id: number = -1;
    name: string = "";
    password: string = "";

    constructor(obj?: Object) {
      obj && Object.assign(this, obj);
    }
}
