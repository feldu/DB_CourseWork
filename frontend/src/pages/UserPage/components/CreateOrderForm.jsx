import React, {useState} from 'react';
import {useDispatch} from "react-redux";
import {Box, Button, Heading} from '@chakra-ui/react';
import AlertMessage from "../../../components/AlertMessage";
import InputText from "../../../components/InputText";
import * as thunks from "../../../redux/thunks";
import {validateCreateOrder} from "../../../utils/validateInput";
import InputSelect from "../../../components/InputSelect";


export default function CreateOrderForm({casteOptions, futureJobTypeOptions}) {
    const [caste, setCaste] = useState('');
    const [humanNumber, setCount] = useState('');
    const [futureJobTypes, setFutureJobs] = useState([]);
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const dispatch = useDispatch();

    const submitHandler = e => {
        e.preventDefault();
        let {isValid, message} = validateCreateOrder({humanNumber, caste});
        setError(!isValid);
        if (!isValid) {
            setErrorMsg(message);
            return;
        }
        dispatch(thunks.addOrder({humanNumber, caste, futureJobTypes}));
    };

    return (
        <Box p={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%">
            <Box textAlign="center">
                <Heading size="lg">Сделать заказ на людей</Heading>
            </Box>
            {error && <AlertMessage status="error" message={errorMsg} maxW="100%"/>}
            <Box my={4} textAlign="left">
                <form>
                    <InputText value={humanNumber} setValue={setCount} label={"Количество"}
                               placeholder={"Положительное целое число"}/>
                    <InputSelect label="Каста" onChangeHandler={e => setCaste(e.value)} placeholder="Выберите касту"
                                 options={casteOptions} isMulti={false} isRequired={true}/>
                    <InputSelect label="Для работы в условиях"
                                 onChangeHandler={(e) => setFutureJobs(Array.isArray(e) ? e.map(x => x.value) : [])}
                                 placeholder="Дополнительные требования" options={futureJobTypeOptions} isMulti={true}/>
                    <Button width="full" mt={4} type="submit" onClick={submitHandler}>Сделать запрос</Button>
                </form>
            </Box>
        </Box>
    );
}