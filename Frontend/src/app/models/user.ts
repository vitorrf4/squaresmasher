export class User {
    id: number = -1;
    name: string = "";
    password: string = "";
    authdata?: string

    constructor(obj?: Object) {
      obj && Object.assign(this, obj);
    }
}
