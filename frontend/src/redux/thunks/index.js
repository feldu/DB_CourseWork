import * as actions from "../actions";
import {changeCurrentOrder, updateFutureJobTypes, updateOrders} from "../actions";
import * as constants from "../constants";
import axios from "axios";

axios.defaults.baseURL = "http://localhost:31510";

export function showMessage(payload) {
    return function (dispatch) {
        dispatch({type: constants.SHOW_MESSAGE, payload});
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
            .catch(() => {
                dispatch(showMessage({message: "Ошибка входа", isError: true}));
            })
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
            .catch();
        dispatch(actions.signOut());
    }
}

export function getUserInfo() {
    return function (dispatch) {
        axios
            .post('/user_info')
            .then(response => {
                if (response.status === 200) {
                    dispatch(actions.signIn(response.data));
                }
            }).catch(e => console.log(e));
    }
}

export function getOrders() {
    return function (dispatch) {
        axios
            .post('/user/get_orders')
            .then(response => {
                dispatch(updateOrders(response.data));
            })
            .catch(e => console.log(e));
    }
}

export function getFutureJobTypes() {
    return function (dispatch) {
        axios
            .post('/user/get_future_job_types')
            .then(response => {
                console.log(response.data);
                dispatch(updateFutureJobTypes(response.data));
            })
            .catch(e => console.log(e));
    }
}


export function addOrder(order) {
    return function (dispatch) {
        axios
            .post('/user/add_order', {
                ...order,
                futureJobTypes: order.futureJobTypes.length === 0 ? [null] : order.futureJobTypes
            })
            .then(response => {
                console.log(response);
                if (response.status === 200) {
                    const id = response.data;
                    dispatch(changeCurrentOrder({...order, id: id}));
                    dispatch(getOrders());
                    //todo: add to user page message like this "Ваш запрос обработан. Отследить его вы можете по № %id"
                }
            })
            .catch(e => console.log(e));
    }
}
