import * as constants from "../constants";


const initialState = {
    currentUser: {username: null, password: null, fullname: null, role: null},
    authorizationInfo: {
        message: null,
        isError: null
    }
};

export const authorizationReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.SIGN_IN:
            return {...state, currentUser: action.payload};
        case constants.SIGN_OUT:
            return {...state, currentUser: {username: null, password: null, fullname: null, role: null}};
        case constants.SHOW_MESSAGE:
            return {...state, authorizationInfo: action.payload};
        case constants.HIDE_MESSAGE:
            return {...state, authorizationInfo: {message: null, isError: null}};
        default:
            return state;
    }
};