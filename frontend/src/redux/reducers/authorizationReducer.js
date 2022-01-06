import * as constants from "../constants";


const initialState = {
    currentUser: {username: null, password: null, fullname: null, role: null},
};

export const authorizationReducer = (state = initialState, action) => {
    switch (action.type) {
        case constants.SIGN_IN:
            return {...state, currentUser: action.payload};
        case constants.SIGN_OUT:
            return {...state, currentUser: {username: null, password: null, fullname: null, role: null}};
        default:
            return state;
    }
};