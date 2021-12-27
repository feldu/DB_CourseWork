import * as actions from "../actions";
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
    return function (dispatch, getState) {
        try {
            axios
                .post('/auth/signup', {
                        username: user.username,
                        password: user.password,
                        fullname: user.fullname,
                        role: user.role
                    },
                    setHeaders(getState().authorization.currentUser))
                .then(response => {
                    dispatch(showMessage({message: response.data, isError: false}))
                })
                .catch(e => {
                    if (e.response.status === 400)
                        dispatch(showMessage({message: e.response.data, isError: true}));
                });
        } catch (e) {
            console.log("SingUp error", e);
        }
    }
}

export function loginUser(user) {
    return function (dispatch, getState) {
        try {
            axios
                .post('/auth/signin', user,
                    setHeaders(getState().authorization.currentUser))
                .then(response => {
                    user = {username: user.username, token: response.data};
                    localStorage.setItem("token", response.data);
                    localStorage.setItem("username", user.username);
                    dispatch(actions.signIn(user));
                })
                .catch(e => {
                    if (e.response.status === 400)
                        dispatch(showMessage({message: e.response.data, isError: true}))
                })
        } catch (e) {
            console.log("SignIn error", e);
        }

    }
}

export function addUser(user, thunk) {
    return function (dispatch) {
        dispatch(thunk(user));
    }
}

export function logout() {
    return function (dispatch) {
        localStorage.removeItem("token");
        localStorage.removeItem("username");
        dispatch(actions.signOut());
    }
}


function setHeaders(currentUser) {
    if (currentUser) {
        return {
            headers: {
                'Authorization': "Bearer " + currentUser.token,
            }
        }
    }
    return {headers: {}}
}