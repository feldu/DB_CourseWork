import * as constants from "../constants";

const username = localStorage.getItem("username"),
    token = localStorage.getItem("token");

const currentUser = (username && token) ? {username: username, token: token} : null;

const initialState = {
    currentUser: currentUser,
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