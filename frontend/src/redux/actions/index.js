import * as constants from "../constants";

export const signIn = payload => ({
    type: constants.SIGN_IN,
    payload,
});
export const signOut = () => ({
    type: constants.SIGN_OUT,
});

export const hideMessage = () => ({
    type: constants.HIDE_MESSAGE,
});