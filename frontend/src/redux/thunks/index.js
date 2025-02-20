import * as actions from "../actions";
import axios from "axios";

axios.defaults.baseURL = "http://localhost:31510";

export function showMessage(message) {
    return function (dispatch) {
        dispatch(actions.showMessage(message));
        setTimeout(() => dispatch(actions.hideMessage()), 3000)
    };
}

export function registerUser(user) {
    return function (dispatch) {
        axios
            .post('/auth/signup', {
                username: user.username,
                password: user.password,
                fullname: user.fullname,
                role: user.role
            })
            .then(response => {
                dispatch(showMessage({message: response.data, isError: false}))
            })
            .catch(e => {
                if (e.response.status === 400)
                    dispatch(showMessage({message: e.response.data, isError: true}));
                else handleError(e, dispatch)
            });

    }
}

export function loginUser(user) {
    return function (dispatch) {
        let formData = new FormData();
        formData.append('username', user.username);
        formData.append('password', user.password);
        axios
            .post('/auth/signin', formData)
            .then(response => {
                if (response.status === 200) {
                    let url = new URL(response.request.responseURL);
                    if (url.searchParams.has("error"))
                        dispatch(showMessage({message: "Не удалось войти", isError: true}));
                    else
                        window.location.href = response.request.responseURL;
                }
            })
            .catch(e => handleError(e, dispatch));
    }
}


export function logout() {
    return function (dispatch) {
        axios
            .post('/auth/logout')
            .then(response => {
                    if (response.status === 200) {
                        window.location.href = response.request.responseURL;
                    }
                }
            )
            .catch(e => handleError(e, dispatch));
        dispatch(actions.signOut());
    }
}

export function getUserInfo() {
    return function (dispatch) {
        axios
            .get('/user/authenticated')
            .then(response => {
                if (response.status === 200) {
                    dispatch(actions.signIn(response.data));
                }
            }).catch(e => handleError(e, dispatch));
    }
}

export function getOrders() {
    return function (dispatch) {
        axios
            .get('/user/orders')
            .then(response => {
                dispatch(actions.updateOrders(response.data));
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function getFutureJobTypes() {
    return function (dispatch) {
        axios
            .get('/user/future-job-types')
            .then(response => {
                dispatch(actions.updateFutureJobTypes(response.data));
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function getCastes() {
    return function (dispatch) {
        axios
            .get('/user/castes')
            .then(response => {
                dispatch(actions.updateCastes(response.data));
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function addOrder(order) {
    return function (dispatch) {
        axios
            .post('/user/orders', {
                ...order,
                futureJobTypes: order.futureJobTypes.length === 0 ? [null] : order.futureJobTypes
            })
            .then(response => {
                if (response.status === 200) {
                    const id = response.data;
                    dispatch(actions.changeCurrentOrder({...order, id: id}));
                    dispatch(getOrders());
                }
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function addMaterial(material) {
    return function (dispatch) {
        axios
            .post('/reviewer/material', {
                ...material,
            })
            .then(response => {
                if (response.status === 200) {
                    dispatch(showMessage({message: "Ваша свинья отправлена", isError: false}));
                }
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function addOvum() {
    return function (dispatch) {
        axios
            .post('/volunteer/ovum')
            .then(response => {
                if (response.status === 200)
                    dispatch(showMessage({message: "Вы записались и ваша яйцеклетка будет вырезана навеки вечные", isError: false}));
                else
                    dispatch(showMessage({message: "Ваша яйцеклетка уже вылизана", isError: true}));
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function getOvumByOrderId(orderId) {
    return function (dispatch) {
        axios
            .get(`/user/ovums/order/${orderId}`)
            .then(response => {
                dispatch(actions.updateOvumByOrder(response.data));
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function getPredeterminers() {
    return function (dispatch) {
        axios
            .get('/admin/users', {params: {roleName: "ROLE_PREDETERMINER"}})
            .then(response => {
                dispatch(actions.updatePredeterminers(response.data));
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function getOrdersById(id) {
    return function (dispatch) {
        axios
            .get(`/admin/orders/human/${id}`)
            .then(response => {
                dispatch(actions.updateOrders(response.data));
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function getFreeOvumCount() {
    return function (dispatch) {
        axios
            .get(`admin/ovums/count/order/${null}`)
            .then(response => {
                dispatch(actions.updateFreeOvumCount(response.data));
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function bindFreeOvumToOrder(orderId, count) {
    return function (dispatch, getState) {
        axios
            .post(`/admin/ovums/bind/${count}/order/${orderId}`)
            .then(response => {
                if (response.status === 200) {
                    const currentPredeterminer = getState().predeterminer.currentPredeterminer;
                    const currentOrder = getState().order.currentOrder;
                    dispatch(showMessage({message: response.data, isError: false}));
                    dispatch(getFreeOvumCount());
                    dispatch(getOrdersById(currentPredeterminer.id));
                    dispatch(actions.changeCurrentOrder({...currentOrder, processing: true}));
                }
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function updateOvum(ovum) {
    return function (dispatch, getState) {
        axios
            .put(`/admin/ovums/${ovum.id}`, ovum)
            .then(response => {
                if (response.status === 200) {
                    dispatch(showMessage({message: response.data, isError: false}));
                    dispatch(getOvumByOrderId(getState().order.currentOrder.id));
                }
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function startFirstStep(orderId) {
    return function (dispatch) {
        axios
            .post('/admin/start/first_step', {orderId})
            .then(response => {
                if (response.status === 200) {
                    dispatch(showMessage({message: response.data, isError: false}));
                    dispatch(getOvumByOrderId(orderId));
                    dispatch(getMovingListByOrderId(orderId));
                    dispatch(getUsingListByOrderId(orderId));
                }
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function startSecondStep(orderId) {
    return function (dispatch) {
        axios
            .post('/admin/start/second_step', {orderId})
            .then(response => {
                if (response.status === 200) {
                    dispatch(showMessage({message: response.data, isError: false}));
                    dispatch(removeExtraOvum(orderId));
                    dispatch(getMovingListByOrderId(orderId));
                    dispatch(getUsingListByOrderId(orderId));
                }
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function removeExtraOvum(orderId) {
    return function (dispatch) {
        axios
            .delete(`/admin/ovums/order/${orderId}`)
            .then(response => {
                if (response.status === 200) {
                    dispatch(getOvumByOrderId(orderId));
                }
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function startThirdStep(orderId) {
    return function (dispatch) {
        axios
            .post('/admin/start/third_step', {orderId})
            .then(response => {
                if (response.status === 200) {
                    dispatch(showMessage({message: response.data, isError: false}));
                    dispatch(getOvumByOrderId(orderId));
                    dispatch(getMovingListByOrderId(orderId));
                    dispatch(getUsingListByOrderId(orderId));
                    dispatch(getAddingListByOrderId(orderId));
                }
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function getMovingListByOrderId(orderId) {
    return function (dispatch) {
        axios
            .get(`/admin/move-ovum-container-to-room/order/${orderId}`)
            .then(response => {
                dispatch(actions.updateMovingByOrder(response.data));
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function getUsingListByOrderId(orderId) {
    return function (dispatch) {
        axios
            .get(`/admin/use-machine-by-ovum-container/order/${orderId}`)
            .then(response => {
                dispatch(actions.updateUsingByOrder(response.data));
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function getAddingListByOrderId(orderId) {
    return function (dispatch) {
        axios
            .get(`/admin/add-material-to-ovum-container/order/${orderId}`)
            .then(response => {
                dispatch(actions.updateAddingByOrder(response.data));
            })
            .catch(e => handleError(e, dispatch));
    }
}

export function removeOrderById(orderId) {
    return function (dispatch) {
        axios
            .delete(`/admin/orders/${orderId}`)
            .then(response => {
                if (response.status === 200) {
                    dispatch(showMessage({message: response.data, isError: false}));
                    dispatch(getOrders());
                    dispatch(actions.changeCurrentOrder({
                        id: null,
                        humanNumber: null,
                        caste: null,
                        futureJobTypes: null,
                        processing: null
                    }));
                }
            })
            .catch(e => handleError(e, dispatch));
    }
}

function handleError(e, dispatch) {
    if (e.response.status === 400)
        dispatch(showMessage({message: "Ошибка пользовательского ввода", isError: true}));
    if (e.response.status === 404)
        dispatch(showMessage({message: "Выбранное вами сущее не существует", isError: true}));
    if (e.response.status === 500)
        dispatch(showMessage({message: "Произошла какая-то...", isError: true}));
}