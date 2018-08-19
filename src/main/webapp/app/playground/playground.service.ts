import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {MSDTO} from "./msdto.model";
import {ApiDoc} from "./apidoc.model";
import {stringifyIgnoringKey} from "../shared/model/json-util";


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable()
export class PlaygroundService {

    routes: MSDTO[];
    response: String;

    constructor(private http: HttpClient) {

    }
    getEndpoints() {
         return this.http.get('api/fwk/endpoints/').map((res: HttpResponse<String>) => res.toString());
    }


    getSessionTemplateHTML()  {
        return this.http.get('playground/full/');
    }
    getOperationJSON(opName) {
        const httpOptions2 = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' , 'Accept' : 'application/json' })
        };
        return this.http.post('playground/op/name/', JSON.stringify({'operationName' : opName}), httpOptions2 );

    }
    getOperationList() {
        return this.http.get('playground/oplist/');
    }
    saveFullSessionTemplate(tpHtml, opHtml) {
        console.log("SAVING FULL TEMPLATE", tpHtml, opHtml);
        const body = JSON.stringify({'htmlTemplate' : tpHtml, 'htmlOperation' : opHtml});
        const headers = new HttpHeaders();
        headers.append( 'Accept', 'application/json');
        headers.append( 'Content-Type', 'application/json');
        return this.http.post('playground/full/', body, httpOptions);
    }
    saveOperation(opName, opJson) {
        console.log("SAVING OP ", opName);
        const opDesc = this.buildOpDesc(opJson, opName);
        const body = stringifyIgnoringKey({'json' : {'operationName' : opName, 'jsonOperation' : opJson}, 'desc' : opDesc}, 'apiDoc');
        console.log("body", body);
        const headers = new HttpHeaders();
        headers.append( 'Accept', 'application/json');
        headers.append( 'Content-Type', 'application/json');
        return this.http.post('playground/op/', body,  httpOptions);
    }
    saveTemplate(tpName, tpJson) {
       //TODO
    }
    private buildOpDesc(json, name) {
        //TODO
        return {
            operationName : name,
            desc : 'mockDesc',
            input : 'string',
            output : 'string'
        }
    }
    getPublicApiList() {
        return this.http.get('playground/public-api/all');
    }



    getMockMS() {
        const apiDoc: ApiDoc = new ApiDoc('', '', '', [''], '', '', '', '');
        const apiDoc2: ApiDoc = new ApiDoc('CAM/1', 'GET', 'PiCam', [''], 'someRequestHeader', 'someREsponseHeader', 'boolean', 'video stream');
        const apiDoc3: ApiDoc = new ApiDoc('DetectLanguage', 'POST', '', [''], 'someRequestHeader', 'someREsponseHeader', 'string', 'string');
        const apiDoc4: ApiDoc = new ApiDoc('translate/lstr', 'POST', '', [''], 'someRequestHeader', 'someREsponseHeader', 'string[2]', 'string');
        const string_to_boolean = (x: string) => {
            return x === 'On' ? true : x === 'Off' ? false : null;
        };
        apiDoc.serviceId = 'Front Light';
        apiDoc.path = 'LED/1';
        apiDoc.reqType = 'POST';
        apiDoc.inputDataType = 'string';
        // playground allow to override the following
        apiDoc.requestHeader = {
            desc: 'Enter "On" to turn on the light, and "Off" to turn it off',
            inputType : 'textarea',
            converter : {
                type : 'string_to_boolean', // OR
                map : {
                    'on': true,
                    'off': false
                }
            },
            outputType : 'none',
        };
        const apiDocs: ApiDoc[] = [];
        apiDocs.push(apiDoc);
        apiDocs.push(apiDoc2);
        const apiDocs2: ApiDoc[] = [];
        apiDocs2.push(apiDoc3);
        apiDocs2.push(apiDoc4);
        const MOCK_IOT2 = new MSDTO(
            'trad_mod/',
            'MOCK_MS_TRAD',
            [],
            'some gatweway properties',
            apiDocs2
    );
        const MOCK_IOT = new MSDTO(
            'IoT/',
            'MOCK_IOT_MS',
            [],
            'some gatweway properties',
            apiDocs
        );
        const mockMses: MSDTO[] = [];
        mockMses.push(MOCK_IOT);
        mockMses.push(MOCK_IOT2);
        return mockMses;
    }
    getMockOp() {
        return [{
            title : 'Mock_Op_1',
            desc : 'Does nothing',
            body : {
                inputType : 'string',
                inputDesc : 'some input',
                outputType : 'json',
                outputDesc : 'some Output'
            }
        }];
    }
    getMockPublicApis() {
        return [{
            title : 'Google Translate =>FR',
            desc : 'translates anything to french',
            body : {
                inputType : 'string',
                outputType : 'json'
            },
            _api : "https://translate.googleapis.com/translate_a/single?client=gtx&sl=auto&tl=fr&dt=t&q={{_}}",
            _transformation : encodeURI
        },
            {
                title : 'Google Translate',
                desc : 'translates any to any',
                body : {
                    inputType : 'string',
                    outputType : 'json'
                },
                _api : "https://translate.googleapis.com/translate_a/single?client=gtx&sl={{from}}&tl={{to}}&dt=t&q={{_}}",
                _transformation : encodeURI,
                _params : {
                    'from' : '*?' ,
                    'to' : '*?'

        }
            }];
    }
    getMockConverters() {
        return [  {
            convType : 'embedded',
            title : 'Automatic',
            desc : 'we\'ll handle that for you',
            body : {
                inputType : 'file',
                outputType : 'json',
                parameters : null
            }
        },{
            convType : 'map',
            title : 'en-str-as-bool',
            desc : 'converts english input to boolean',
            body : {
                inputType : 'string',
                outputType : 'boolean',
                contentType : 'json',
                content : [
                    {on : true},
                    {off : true},
                    {true : true},
                    {false : false},
                    {yes : false},
                    {no : false}
                ]
            }
        },
          ];
    }
}
