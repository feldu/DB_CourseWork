export const validateInput = ({username, password}) => {
    if ((username.length < 4 || username.length > 20) && (password.length < 4 || password.length > 20))
        return {isValid: false, message: "Логин и пароль должны содержать от 4 до 20 символов"};
    if (username.length < 4 || username.length > 20)
        return {isValid: false, message: "Логин должен содержать от 4 до 20 символов"};
    else if (password.length < 4 || password.length > 20)
        return {isValid: false, message: "Пароль должен содержать от 4 до 20 символов"};
    return {isValid: true, message: null};
};