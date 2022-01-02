import {combineReducers} from "redux";
import {authorizationReducer} from "./authorizationReducer";
import {orderReducer} from "./orderReducer";

export const rootReducer = combineReducers({
    authorization: authorizationReducer,
    order: orderReducer
});