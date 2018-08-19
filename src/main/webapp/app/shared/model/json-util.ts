import {FastMap} from "rxjs/util/FastMap";
export const circularSafeStringify = function (v) {
    const cache = new FastMap();
    return JSON.stringify(v, function (key, value) {
        if (typeof value === 'object' && value !== null) {
            if (cache.get(value)) {
                // Circular reference found, discard key
                return;
            }
            // Store value in our map
            cache.set(value, true);
        }
        return value;
    });
};
export const stringifyIgnoringKey = function (obj, k) {
    return JSON.stringify(obj, function (key, value) {
        if (key===k) {
           return
        }
        return value;
    });
};
