import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {GatewayRoute} from "../admin/gateway/gateway-route.model";
import {SimpleJsonRestComponent} from "./simple-json-rest.component";
import {HeroProfileComponent} from "./hero-profile.component";
import {MSTemplateConfigItem} from "./mstemplate-config-item";
import {MsEntry} from "./ms-entry.model";
import {Observable} from "rxjs/Observable";

@Injectable()
export class MSTemplateService {
    gatewayRoutes: GatewayRoute[];
    msEntries: MsEntry[];

    // load gtway.json from all registered microservices
    // next step : get only some ms depending on conditions and availability
    constructor(private http: HttpClient) {
        this.findAll();
    }

    loadTemplates() {
        return this.msEntries;
    }
    findAll() {
        this.http.get('api/gateway/routes/').map((res: HttpResponse<GatewayRoute[]>) => res.body).subscribe((gatewayRoutes) => {
            this.gatewayRoutes = gatewayRoutes;
            this.msEntries = gatewayRoutes.map((route: GatewayRoute) => new MsEntry(route.path, route.serviceId, route.serviceInstances, null));
            let mst: MsEntry[] = [];
            this.msEntries.forEach((msEntry)=>{
                //todo check if entry has a prop file
                if (msEntry.serviceId!="uaa") {
                    mst.push(msEntry);
                    //get properties
                    this.getGtwJson('http://localhost:8080'+msEntry.path.split('*')[0].concat('fwk/msGtwayProperties/')).subscribe((data:string)=>{
                        console.log("DEBUG----------");
                        console.log(data);
                        msEntry.gtwayProperties = data;
                    });

                }
            });
            this.gatewayRoutes = mst;

        })
    }
    getGtwJson(path : string): Observable<string> {
       return this.http.get(path).map((res: HttpResponse<string>) => res.body);
    }
    getMsTemplates() {



        let test = "TEST";

    return [
      new MSTemplateConfigItem(HeroProfileComponent, {name: test, bio: 'Brave as they come'}),

      new MSTemplateConfigItem(HeroProfileComponent, {name: 'Dr IQ', bio: 'Smart as they come'}),

      new MSTemplateConfigItem(SimpleJsonRestComponent,   {title: 'MicroserviceTest001',
                                        body: 'Submit your resume today!'}),

      new MSTemplateConfigItem(SimpleJsonRestComponent,   {title: 'MicroserviceTest002',
                                        body: 'Apply today'}),
    ];

  }
}
