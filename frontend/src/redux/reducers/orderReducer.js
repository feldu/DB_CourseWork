import {ADD_ORDER} from "../constants";

const initialState = {
    order_ids: []
};


export const orderReducer = (state = initialState, action) => {
    switch (action) {
        case ADD_ORDER:
            return {...state, order_ids: [...state.order_ids, action.payload]};
        default:
            return state;
    }
};