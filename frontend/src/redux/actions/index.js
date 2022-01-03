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

export const updateOrders = orders => ({
    type: constants.UPDATE_ORDERS,
    payload: orders
});

export const changeCurrentOrder = order => ({
    type: constants.CHANGE_CURRENT_ORDER,
    payload: order
});
