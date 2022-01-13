import * as constants from "../constants";


const initialState = {
    movingList: [],
    usingList: [],
    addingList: []
};

export const historyReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.UPDATE_MOVING:
            return {...state, movingList: action.payload};
        case constants.UPDATE_USING:
            return {...state, usingList: action.payload};
        case constants.UPDATE_ADDING:
            return {...state, addingList: action.payload};
        default:
            return state;
    }
};