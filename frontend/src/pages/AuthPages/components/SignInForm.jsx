import React, {useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Box, Button, Flex, Heading,} from '@chakra-ui/react';

import {validateSignInInput} from "../../../utils/validateInput";
import * as thunks from "../../../redux/thunks";
import AlertMessage from "../../../components/AlertMessage";
import InputText from "../../../components/InputText";
import InputPassword from "../../../components/InputPassword";


export default function SignInForm() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const authInfo = useSelector(state => state.message.messageInfo);
    const dispatch = useDispatch();


    const signIn = async e => {
        e.preventDefault();
        let {isValid, message} = validateSignInInput({username, password});
        setError(!isValid);
        if (!isValid) {
            setErrorMsg(message);
            return;
        }
        dispatch(thunks.loginUser({username, password}));
    };
    return (
        <Flex width="full" align="center" justifyContent="center" alignItems="center" direction="vertical" flex={1}>
            <Box p={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w={400}>
                <Box textAlign="center">
                    <Heading size="lg">Вход</Heading>
                </Box>
                {error && <AlertMessage status="error" message={errorMsg} maxW="100%"/>}
                <Box my={4} textAlign="left">
                    <form>
                        <InputText value={username} setValue={setUsername} placeholder="От 4 до 20 символов"
                                   label="Логин"/>
                        <InputPassword value={password} setValue={setPassword} placeholder="От 4 до 20 символов"
                                       label="Пароль"/>
                        <Button width="full" mt={4} type="submit" onClick={signIn}>Вход</Button>
                    </form>
                    {authInfo.message &&
                    <AlertMessage message={authInfo.message} status={authInfo.isError ? "error" : "success"}/>}
                </Box>
            </Box>
        </Flex>
    );
}