import * as constants from "../constants";


const initialState = {
    //todo: change current user structure (must contain
    // 1. username
    // 2. password
    // 3. fullname
    // 4. role
    currentUser: null,
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
            return {...state, currentUser: null};
        case constants.SHOW_MESSAGE:
            return {...state, authorizationInfo: action.payload};
        case constants.HIDE_MESSAGE:
            return {...state, authorizationInfo: {message: null, isError: false}};
        default:
            return state;
    }
};