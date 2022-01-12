import * as constants from "../constants";

const initialState = {
    freeOvumCount: null,
    ovumByOrder: []
};


export const ovumReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.UPDATE_FREE_OVUM_COUNT:
            return {...state, freeOvumCount: action.payload};
        case constants.UPDATE_OVUM_BY_ORDER:
            return {...state, ovumByOrder: action.payload};
        default:
            return state;
    }
};