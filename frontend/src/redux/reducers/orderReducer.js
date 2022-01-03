import {CHANGE_CURRENT_ORDER, UPDATE_ORDERS} from "../constants";

const initialState = {
    orders: [],
    currentOrder: {
        id: null,
        humanNumber: null,
        caste: null,
        futureJobTypes: null
    }
};


export const orderReducer = (state = initialState, action) => {
    switch (action.type) {
        case UPDATE_ORDERS:
            return {...state, orders: action.payload};
        case CHANGE_CURRENT_ORDER:
            return {...state, currentOrder: action.payload};
        default:
            return state;
    }
};