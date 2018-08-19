import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable()
export class DashboardService {

    constructor(private http: HttpClient) {

    }

    public savePublicApi(obj) {
        console.log("SAVING API "+obj.name);
        const body = JSON.stringify(obj);
        console.log("body", body);
        const headers = new HttpHeaders();
        headers.append( 'Accept', 'application/json');
        headers.append( 'Content-Type', 'application/json');
        return this.http.post('playground/public-api/', body,  httpOptions);
    }

}
