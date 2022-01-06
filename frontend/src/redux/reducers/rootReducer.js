import {combineReducers} from "redux";
import {authorizationReducer} from "./authorizationReducer";
import {orderReducer} from "./orderReducer";
import {predeterminerReducer} from "./predeterminerReducer";
import {messageReducer} from "./messageReducer";

export const rootReducer = combineReducers({
    message: messageReducer,
    authorization: authorizationReducer,
    order: orderReducer,
    predeterminer: predeterminerReducer
});