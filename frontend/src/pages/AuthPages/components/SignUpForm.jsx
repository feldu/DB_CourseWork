import React, {useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {
    Box,
    Button,
    Flex,
    FormControl,
    FormLabel,
    Heading,
    Icon,
    Input,
    InputGroup,
    InputRightElement,
    Select
} from '@chakra-ui/react';
import {ViewIcon, ViewOffIcon} from '@chakra-ui/icons';
import {validateSignUpInput} from "../../../utils/validateInput";
import * as thunks from "../../../redux/thunks";
import AlertMessage from "../../../components/AlertMessage";
import {useNavigate} from "react-router-dom";


export default function SignInForm() {
    const [show, setShow] = React.useState(false);
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
        navigate("/auth/signin");
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
                                    <Button h='1.75rem' size='sm' onClick={() => setShow(!show)}>
                                        <Icon as={show ? ViewOffIcon : ViewIcon}/>
                                    </Button>
                                </InputRightElement>
                            </InputGroup>
                        </FormControl>
                        <FormControl mt={6} isRequired={true}>
                            <FormLabel>ФИО</FormLabel>
                            <Input type="text"
                                   placeholder="Не больше 64 символов"
                                   value={fullname}
                                   onChange={e => {
                                       setFullname(e.target.value)
                                   }}
                            />
                        </FormControl>
                        <FormControl mt={6} isRequired={true} w="fill">
                            <Select onChange={e => setRole(e.target.value)}>
                                <option disabled value="">Выберите профессию</option>
                                <option defaultValue value="predeterminer">Предопределитель</option>
                            </Select>
                        </FormControl>
                        <Button width="full" mt={4} type="submit" onClick={signUp}>Регистрация</Button>
                    </form>
                    {authInfo.message &&
                    <AlertMessage message={authInfo.message} status={authInfo.isError ? "error" : "success"}/>}
                </Box>
            </Box>
        </Flex>
    );
}