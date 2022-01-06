import * as constants from "../constants";

const initialState = {
    messageInfo: {
        message: null,
        isError: null
    }
};

export const messageReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.SHOW_MESSAGE:
            return {...state, messageInfo: action.payload};
        case constants.HIDE_MESSAGE:
            return {...state, messageInfo: {message: null, isError: null}};
        default:
            return state;
    }
};