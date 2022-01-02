import * as constants from "../constants";

export const signIn = currentUser => ({
    type: constants.SIGN_IN,
    payload: currentUser
});
export const signOut = () => ({
    type: constants.SIGN_OUT,
});

export const hideMessage = () => ({
    type: constants.HIDE_MESSAGE,
});

export const addOrderIndex = index => ({
    type: constants.ADD_ORDER,
    payload: index
});