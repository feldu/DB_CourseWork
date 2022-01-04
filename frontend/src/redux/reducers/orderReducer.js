import {CHANGE_CURRENT_ORDER, UPDATE_FUTURE_JOB_TYPES, UPDATE_ORDERS} from "../constants";

const initialState = {
    orders: [],
    currentOrder: {
        id: null,
        humanNumber: null,
        caste: null,
        futureJobTypes: null
    },
    futureJobTypes: []

};


export const orderReducer = (state = initialState, action) => {
    switch (action.type) {
        case UPDATE_ORDERS:
            return {...state, orders: action.payload};
        case CHANGE_CURRENT_ORDER:
            return {...state, currentOrder: action.payload};
        case UPDATE_FUTURE_JOB_TYPES:
            return {...state, futureJobTypes: action.payload};
        default:
            return state;
    }
};