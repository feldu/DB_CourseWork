import * as constants from "../constants";

const initialState = {
    orders: [],
    castes: [],
    currentOrder: {
        id: null,
        humanNumber: null,
        caste: null,
        futureJobTypes: null,
        processing: null
    },
    futureJobTypes: []

};


export const orderReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.UPDATE_ORDERS:
            return {...state, orders: action.payload};
        case constants.CHANGE_CURRENT_ORDER:
            return {...state, currentOrder: action.payload};
        case constants.UPDATE_FUTURE_JOB_TYPES:
            return {...state, futureJobTypes: action.payload};
        case constants.UPDATE_CASTES:
            return {...state, castes: action.payload};
        default:
            return state;
    }
};