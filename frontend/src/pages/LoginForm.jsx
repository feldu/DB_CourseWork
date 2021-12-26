import React, {useState} from 'react';
import {
    Box,
    Button,
    CircularProgress,
    Flex,
    FormControl,
    FormLabel,
    Heading,
    Icon,
    Input,
    InputGroup,
    InputRightElement,
} from '@chakra-ui/react';
import {ViewIcon, ViewOffIcon} from '@chakra-ui/icons';
import ErrorMessage from "../components/ErrorMessage";
import {validateInput} from "../utils/validateInput";


export default function LoginForm() {
    const [show, setShow] = React.useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const toggleShowPassword = () => setShow(!show);
    const signUp = async e => {
        e.preventDefault();
        handleSubmit({username, password}, thunks.registerUser);
        setPassword("");
    };

    const signIn = async e => {
        e.preventDefault();
        handleSubmit({username, password}, thunks.loginUser);
    };

    const handleSubmit = ({username, password}, auth_func) => {
        let {isValid, message} = validateInput({username, password});
        setError(!isValid);
        if (!isValid) {
            setErrorMsg(message);
            return;
        }
        setIsLoading(true);
        try {
            //await dispatch(thunks.addUser({username, password}, auth_func));
            setIsLoading(false);
        } catch (error) {
            setIsLoading(false);
            setError(true);
            setErrorMsg("Не удалось авторизоваться");
        }
    };
    return (
        <Flex width="full" align="center" justifyContent="center">
            <Box p={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w={400}>
                <Box textAlign="center">
                    <Heading size="lg">Авторизация</Heading>
                </Box>
                {error && <ErrorMessage message={errorMsg} maxW="100%"/>}
                <Box my={4} textAlign="left">
                    <form>
                        <FormControl isRequired={true}>
                            <FormLabel>Логин</FormLabel>
                            <Input type="text"
                                   placeholder="От 4 до 20 символов"
                                   value={username}
                                   onChange={e => {
                                       setUsername(e.target.value)
                                   }}
                            />
                        </FormControl>
                        <FormControl mt={6} isRequired={true}>
                            <FormLabel>Пароль</FormLabel>
                            <InputGroup size='md'>
                                <Input
                                    type={show ? 'text' : 'password'}
                                    placeholder="От 4 до 20 символов"
                                    value={password}
                                    onChange={e => {
                                        setPassword(e.target.value)
                                    }}
                                />
                                <InputRightElement width='4.5rem'>
                                    <Button h='1.75rem' size='sm' onClick={toggleShowPassword}>
                                        <Icon as={show ? ViewOffIcon : ViewIcon}/>
                                    </Button>
                                </InputRightElement>
                            </InputGroup>
                        </FormControl>
                        <Button width="full" mt={4} type="submit" onClick={signUp}>
                            {isLoading ? (
                                <CircularProgress isIndeterminate size="24px" color="teal"/>) : ('Регистрация')}
                        </Button>
                        <Button width="full" mt={4} type="submit" onClick={signIn}>
                            {isLoading ? (<CircularProgress isIndeterminate size="24px" color="teal"/>) : ('Вход')}
                        </Button>
                    </form>
                </Box>
            </Box>
        </Flex>
    );
}