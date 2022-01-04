import React, {useState} from 'react';
import {useDispatch} from "react-redux";
import {Box, Button, FormControl, FormLabel, Heading} from '@chakra-ui/react';
import Select from 'react-select'
import AlertMessage from "../../../components/AlertMessage";
import InputText from "../../../components/InputText";
import * as thunks from "../../../redux/thunks";
import {validateCreateOrder} from "../../../utils/validateInput";


export default function CreateOrderForm({casteOptions, futureJobType}) {
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

                    {
                        //todo: make inputRoleSelect general for all Select components and extract this shit in it
                    }
                    <FormControl my={6} isRequired={true}>
                        <FormLabel>Каста</FormLabel>
                        <Select onChange={e => {
                            setCaste(e.value)
                        }}
                                placeholder="Выберите касту"
                                name="colors"
                                options={casteOptions}
                                className="basic-single"
                                classNamePrefix="select"
                        />
                    </FormControl>
                    <FormControl my={6}>
                        <FormLabel>Для работы в условиях</FormLabel>
                        <Select onChange={(e) => setFutureJobs(Array.isArray(e) ? e.map(x => x.value) : [])}
                                placeholder="Дополнительные требования"
                                isMulti
                                name="colors"
                                options={futureJobType}
                                className="basic-multi-select"
                                classNamePrefix="select"
                        />
                    </FormControl>
                    <Button width="full" mt={4} type="submit" onClick={submitHandler}>Сделать запрос</Button>
                </form>
            </Box>
        </Box>
    );
}