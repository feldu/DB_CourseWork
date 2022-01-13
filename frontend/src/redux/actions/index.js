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

export const updatePredeterminers = predeterminers => ({
    type: constants.UPDATE_PREDETERMINERS,
    payload: predeterminers
});

export const changeCurrentPredeterminer = predeterminer => ({
    type: constants.CHANGE_CURRENT_PREDETERMINER,
    payload: predeterminer
});


export const updateFreeOvumCount = ovumCount => ({
    type: constants.UPDATE_FREE_OVUM_COUNT,
    payload: ovumCount
});


export const updateOvumByOrder = ovum => ({
    type: constants.UPDATE_OVUM_BY_ORDER,
    payload: ovum
});

export const updateMovingByOrder = movingList => ({
    type: constants.UPDATE_MOVING,
    payload: movingList
});

export const updateUsingByOrder = usingList => ({
    type: constants.UPDATE_USING,
    payload: usingList
});

export const updateAddingByOrder = addingList => ({
    type: constants.UPDATE_ADDING,
    payload: addingList
});