import React, {useState} from "react";
import Flex from "@chakra-ui/core/dist/Flex";
import MainHeader from "../../components/MainHeader";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../redux/thunks";
import {Box, Button, Heading} from "@chakra-ui/react";
import AlertMessage from "../../components/AlertMessage";
import InputText from "../../components/InputText";


export default function VolunteerPage() {
    const [date, setDate] = useState('17.02.2025');
    const [time, setTime] = useState('10:00');
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const dispatch = useDispatch();
    const messageInfo = useSelector(state => state.message.messageInfo);


    const validateTime = time => {
        const timePattern = /^([01]?[0-9]|2[0-3]):([0-5][0-9])$/;
        if (!timePattern.test(time)) {
            return {isValid: false, message: "Неверный формат времени (например, 10:00)"};
        }
        return {isValid: true, message: ""};
    };


    const submitHandler = e => {
        e.preventDefault();
        let {isValid, message} = validateTime(time);
        setError(!isValid);
        if (!isValid) {
            setErrorMsg(message);
            return;
        }
        dispatch(thunks.addOvum());
    };

    return (
        <Flex direction="column">
            <MainHeader/>
            <Box p={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%" maxW={500}>
                <Box textAlign="center">
                    <Heading size="lg">Запись добровольца</Heading>
                </Box>
                {error && <AlertMessage status="error" message={errorMsg} maxW="100%"/>}
                <Box my={4} textAlign="left">
                    <form>
                        <InputText value={date} setValue={setDate} label={"Дата"}
                                   placeholder={""}/>
                        <InputText value={time} setValue={setTime} label={"Время"}
                                   placeholder={""}/>

                        <Button width="full" mt={4} type="submit" onClick={submitHandler}>Записаться</Button>
                    </form>
                    {messageInfo.message &&
                        <AlertMessage message={messageInfo.message} status={messageInfo.isError ? "error" : "success"}/>}
                </Box>
            </Box>
        </Flex>
    );
}