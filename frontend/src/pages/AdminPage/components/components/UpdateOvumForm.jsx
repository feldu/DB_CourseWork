import {Box, Button, Flex, Heading} from "@chakra-ui/react";
import React, {useState} from "react";
import InputSelect from "../../../../components/InputSelect";
import InputText from "../../../../components/InputText";
import * as thunks from "../../../../redux/thunks";
import {useDispatch} from "react-redux";
import AlertMessage from "../../../../components/AlertMessage";

export default function UpdateOvumForm({ovumList}) {
    const dispatch = useDispatch();
    const [id, setId] = useState("");
    const [bud, setBud] = useState("");
    const [fertilizationTime, setFertilizationTime] = useState("");
    const [embryoTime, setEmbryoTime] = useState("");
    const [babyTime, setBabyTime] = useState("");

    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');

    const idChangeHandler = e => {
        const ovumFromList = ovumList.find(o => o.id === e.value);
        setId(ovumFromList.id == null ? "" : ovumFromList.id);
        setBud(ovumFromList.bud == null ? "" : ovumFromList.bud);
        setFertilizationTime(ovumFromList.fertilizationTime === null ? "" : ovumFromList.fertilizationTime);
        setEmbryoTime(ovumFromList.embryoTime === null ? "" : ovumFromList.embryoTime);
        setBabyTime(ovumFromList.babyTime === null ? "" : ovumFromList.babyTime);
    };

    const onClickHandler = e => {
        e.preventDefault();
        //todo: client validation
        dispatch(thunks.updateOvum({id, bud, fertilizationTime, embryoTime, babyTime}));
    };

    return (
        <Box textAlign="center">
            <Heading mt={6} size="lg">Обновить яйцеклетку</Heading>
            {error && <AlertMessage status="error" message={errorMsg} maxW="100%"/>}
            <form>
                <Flex justifyContent="space-between">
                    <InputSelect w={"25%"} mx={5} options={ovumList.map(o => ({value: o.id, label: `№${o.id}`}))}
                                 isRequired={true} placeholder={"№"}
                                 onChangeHandler={idChangeHandler}/>
                    <InputText w={"30%"} mx={5} placeholder={"Является почкой"} value={bud} setValue={setBud}/>
                    <InputText w={"50%"} mx={5} placeholder={"Время оплодотворения"} value={fertilizationTime}
                               setValue={setFertilizationTime}/>
                    <InputText w={"50%"} mx={5} placeholder={"Время зародыша"} value={embryoTime}
                               setValue={setEmbryoTime}/>
                    <InputText w={"50%"} mx={5} placeholder={"Время ребёнка"} value={babyTime} setValue={setBabyTime}/>
                    <Button w="50%" mx={5} my={6} colorScheme='teal' variant='solid' type="submit"
                            onClick={onClickHandler}>Изменить</Button>
                </Flex>
            </form>
        </Box>
    );
}