import * as constants from "../constants";

export const signIn = currentUser => ({
    type: constants.SIGN_IN,
    payload: currentUser
});
export const signOut = () => ({
    type: constants.SIGN_OUT,
});

export const showMessage = message => ({
    type: constants.SHOW_MESSAGE,
    payload: message
});

export const hideMessage = () => ({
    type: constants.HIDE_MESSAGE,
});

export const changeCurrentOrder = order => ({
    type: constants.CHANGE_CURRENT_ORDER,
    payload: order
});

export const updateOrders = orders => ({
    type: constants.UPDATE_ORDERS,
    payload: orders
});

export const updateFutureJobTypes = types => ({
    type: constants.UPDATE_FUTURE_JOB_TYPES,
    payload: types
});

export const updateCastes = castes => ({
    type: constants.UPDATE_CASTES,
    payload: castes
});