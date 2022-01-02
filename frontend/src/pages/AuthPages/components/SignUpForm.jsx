import React, {useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Box, Button, Flex, Heading} from '@chakra-ui/react';
import {validateSignUpInput} from "../../../utils/validateInput";
import * as thunks from "../../../redux/thunks";
import AlertMessage from "../../../components/AlertMessage";
import {useNavigate} from "react-router-dom";
import InputText from "../../../components/InputText";
import InputPassword from "../../../components/InputPassword";
import InputRoleSelect from "../../../components/InputRoleSelect";


export default function SignInForm() {
    const [username, setUsername] = useState('');
    const [fullname, setFullname] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('predeterminer');
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const authInfo = useSelector(state => state.authorization.authorizationInfo);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const signUp = async e => {
        e.preventDefault();
        let {isValid, message} = validateSignUpInput({username, password, fullname, role});
        setError(!isValid);
        if (!isValid) {
            setErrorMsg(message);
            return;
        }
        dispatch(thunks.registerUser({username, password, fullname, role}));
        if (!authInfo.isError) navigate("/auth/signin");
    };

    return (
        <Flex width="full" align="center" justifyContent="center" alignItems="center" direction="vertical" flex={1}>
            <Box p={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w={400}>
                <Box textAlign="center">
                    <Heading size="lg">Регистрация</Heading>
                </Box>
                {error && <AlertMessage status="error" message={errorMsg} maxW="100%"/>}
                <Box my={4} textAlign="left">
                    <form>
                        <InputText value={username} setValue={setUsername} placeholder="От 4 до 20 символов"
                                   label="Логин"/>
                        <InputPassword value={password} setValue={setPassword} placeholder="От 4 до 20 символов"
                                       label="Пароль"/>
                        <InputText value={fullname} setValue={setFullname} placeholder="Не больше 64 символов"
                                   label="ФИО"/>
                        <InputRoleSelect setValue={setRole}/>
                        <Button width="full" mt={4} type="submit" onClick={signUp}>Регистрация</Button>
                    </form>
                    {authInfo.message &&
                    <AlertMessage message={authInfo.message} status={authInfo.isError ? "error" : "success"}/>}
                </Box>
            </Box>
        </Flex>
    );
}