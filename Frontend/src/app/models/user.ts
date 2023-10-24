export class User {
    id: number = 0;
    name: string = "";
    password: string = "";
    accessToken?: string

    constructor(obj?: Object) {
      obj && Object.assign(this, obj);
    }
}
