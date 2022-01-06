import {combineReducers} from "redux";
import {authorizationReducer} from "./authorizationReducer";
import {orderReducer} from "./orderReducer";
import {predeterminerReducer} from "./predeterminerReducer";

export const rootReducer = combineReducers({
    authorization: authorizationReducer,
    order: orderReducer,
    predeterminer: predeterminerReducer
});