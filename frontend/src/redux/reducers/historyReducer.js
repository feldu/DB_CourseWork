import * as constants from "../constants";


const initialState = {
    movingList: [],
    usingList: []
};

export const historyReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.UPDATE_MOVING:
            return {...state, movingList: action.payload};
        case constants.UPDATE_USING:
            return {...state, usingList: action.payload};
        default:
            return state;
    }
};