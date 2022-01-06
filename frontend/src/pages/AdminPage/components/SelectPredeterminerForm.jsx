import {useDispatch, useSelector} from "react-redux";
import React, {useEffect, useState} from "react";
import {Box} from "@chakra-ui/react";
import InputSelect from "../../../components/InputSelect";
import {changeCurrentPredeterminer} from "../../../redux/actions";
import CurrentPredeterminerBox from "./components/CurrentPredeterminerBox";
import SelectCurrentPredeterminerOrder from "./components/SelectCurrentPredeterminerOrder";

export default function SelectPredeterminerForm({predeterminers}) {
    const dispatch = useDispatch();
    const currentPredeterminer = useSelector(state => state.predeterminer.currentPredeterminer);
    const [currentPredeterminerOptions, setCurrentPredeterminerOptions] = useState([]);

    useEffect(() => {
        setCurrentPredeterminerOptions(predeterminers.map(predeterminer => ({
            value: predeterminer.id,
            label: `№${predeterminer.id}: ${predeterminer.fullname}`
        })))
    }, [predeterminers]);

    return (
        <Box py={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%" h="100%">
            <CurrentPredeterminerBox currentPredeterminer={currentPredeterminer}/>
            <form>
                <InputSelect
                    label={"Посмотреть предопределителя"}
                    onChangeHandler={e => dispatch(changeCurrentPredeterminer(predeterminers.find(p => p.id === e.value)))}
                    placeholder={"Выберите предопределителя"}
                    options={currentPredeterminerOptions}
                />
            </form>
            {currentPredeterminer.id !== null &&
            <SelectCurrentPredeterminerOrder currentPredeterminer={currentPredeterminer}/>}
        </Box>
    );
}