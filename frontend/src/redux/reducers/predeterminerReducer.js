import * as constants from "../constants";

const initialState = {
    predeterminers: [],
    currentPredeterminer: {
        id: null,
        fullname: null
    },
};


export const predeterminerReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.UPDATE_PREDETERMINERS:
            return {...state, predeterminers: action.payload};
        case constants.CHANGE_CURRENT_PREDETERMINER:
            return {...state, currentPredeterminer: action.payload};
        default:
            return state;
    }
};