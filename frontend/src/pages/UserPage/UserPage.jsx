import React, {useEffect} from "react";
import Flex from "@chakra-ui/core/dist/Flex";
import MainHeader from "../../components/MainHeader";
import {useDispatch, useSelector} from "react-redux";
import {getFutureJobTypes, getUserInfo} from "../../redux/thunks";
import CreateOrderForm from "./components/CreateOrderForm";
import SelectOrderForm from "./components/SelectOrderForm";


export default function UserPage() {
    const dispatch = useDispatch();
    const casteOptions = [{value: 'Alpha', label: 'Альфа'},
        {value: 'Beta', label: 'Бета'},
        {value: 'Gamma', label: 'Гамма'},
        {value: 'Delta', label: 'Дельта'},
        {value: 'Epsilon', label: 'Эпсилон'}];
    //todo: get from the server db (value = label)
    const futureJobType = useSelector(state => state.order.futureJobTypes);
    // const futureJobType = [{value: 'high-temp', label: 'high-temp'},
    //     {value: 'low-temp', label: 'low-temp'},
    //     {value: 'high-oxxxy', label: 'high-oxxxy'},
    //     {value: 'low-oxxxy', label: 'low-oxxxy'}];
    useEffect(() => {
        dispatch(getUserInfo());
        dispatch(getFutureJobTypes());
    }, []);

    return (
        <Flex direction="column">
            <MainHeader/>
            <Flex w="100%" align="center" justifyContent="left" alignItems="stretch" flex={1}
                  m={5}>
                <Flex direction="column" width="25%" alignItems="stretch">
                    <CreateOrderForm casteOptions={casteOptions} futureJobType={futureJobType}/>
                    <SelectOrderForm casteOptions={casteOptions}/>
                </Flex>
            </Flex>
        </Flex>
    );
}