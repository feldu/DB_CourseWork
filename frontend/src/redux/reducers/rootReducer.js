import {combineReducers} from "redux";
import {authorizationReducer} from "./authorizationReducer";
import {orderReducer} from "./orderReducer";
import {predeterminerReducer} from "./predeterminerReducer";
import {messageReducer} from "./messageReducer";
import {ovumReducer} from "./ovumReducer";
import {historyReducer} from "./historyReducer";

export const rootReducer = combineReducers({
    message: messageReducer,
    authorization: authorizationReducer,
    order: orderReducer,
    predeterminer: predeterminerReducer,
    ovum: ovumReducer,
    history: historyReducer
});