import {
    CHANGE_CURRENT_PREDETERMINER,
    CHANGE_CURRENT_PREDETERMINER_ORDER,
    UPDATE_FREE_OVUM_COUNT,
    UPDATE_PREDETERMINER_ORDERS,
    UPDATE_PREDETERMINERS
} from "../constants";

const initialState = {
    predeterminers: [],
    orders: [],
    freeOvumCount: null,
    currentOrder: {
        id: null,
        humanNumber: null,
        caste: null,
        futureJobTypes: null,
        processing: null
    },
    currentPredeterminer: {
        id: null,
        fullname: null
    }
};


export const predeterminerReducer = (state = initialState, action) => {
    switch (action.type) {
        case UPDATE_PREDETERMINERS:
            return {...state, predeterminers: action.payload};
        case CHANGE_CURRENT_PREDETERMINER:
            return {...state, currentPredeterminer: action.payload};
        case UPDATE_PREDETERMINER_ORDERS:
            return {...state, orders: action.payload};
        case CHANGE_CURRENT_PREDETERMINER_ORDER:
            return {...state, currentOrder: action.payload};
        case UPDATE_FREE_OVUM_COUNT:
            return {...state, freeOvumCount: action.payload};
        default:
            return state;
    }
};