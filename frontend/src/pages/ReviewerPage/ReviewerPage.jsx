import React, {useEffect, useState} from "react";
import Flex from "@chakra-ui/core/dist/Flex";
import MainHeader from "../../components/MainHeader";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../redux/thunks";
import {Box, Button, Heading, Text} from "@chakra-ui/react";
import AlertMessage from "../../components/AlertMessage";
import InputText from "../../components/InputText";


export default function ReviewerPage() {
    const generateRandomPig = () => Math.floor(Math.random() * (228) + 66);

    const [randomPig, setRandomPig] = useState(generateRandomPig)
    const [randomGoodPig, setRandomGoodPig] = useState(randomPig)
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const dispatch = useDispatch();
    const messageInfo = useSelector(state => state.message.messageInfo);

    useEffect(() => {
        setRandomGoodPig(randomPig)
    }, [randomPig])

    const validateRandomPig = (inputValue) => {
        const min = 50;
        const max = randomPig;

        if (inputValue < min) {
            return {
                isValid: false,
                message: `Значение должно быть больше или равно ${min}. Текущее значение: ${randomGoodPig}`,
            };
        }
        if (inputValue > max) {
            return {
                isValid: false,
                message: `Значение должно быть меньше или равно ${max}. Текущее значение: ${randomGoodPig}`,
            };
        }

        return { isValid: true, message: "" };
    };


    const submitHandler = e => {
        e.preventDefault();
        let {isValid, message} = validateRandomPig(randomGoodPig);
        setError(!isValid);
        if (!isValid) {
            setErrorMsg(message);
            return;
        }
        dispatch(thunks.addMaterial({size: parseInt(randomGoodPig)}));
        setRandomPig(generateRandomPig())
    };

    return (
        <Flex direction="column">
            <MainHeader/>
            <Box p={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%" maxW={500}>
                <Box textAlign="center">
                    <Heading size="lg">Оценка свиной туши</Heading>
                    <Text>Свиная туша <b>LITTLE-STUPID-PIG</b> прибыла в размере <b>{randomPig} кв.м.</b></Text>
                </Box>
                {error && <AlertMessage status="error" message={errorMsg} maxW="100%"/>}
                <Box my={4} textAlign="left">
                    <form>
                        <InputText value={randomGoodPig} setValue={setRandomGoodPig} label={"Качественной туши:"}
                                   placeholder={""}/>
                        <Button width="full" mt={4} type="submit" onClick={submitHandler}>Отправить на резку</Button>
                    </form>
                    {messageInfo.message &&
                        <AlertMessage message={messageInfo.message} status={messageInfo.isError ? "error" : "success"}/>}
                </Box>
            </Box>
        </Flex>
    );
}