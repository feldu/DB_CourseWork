import React, {useEffect} from "react";
import Flex from "@chakra-ui/core/dist/Flex";
import MainHeader from "../../components/MainHeader";
import {useDispatch, useSelector} from "react-redux";
import {getFutureJobTypes, getUserInfo} from "../../redux/thunks";
import CreateOrderForm from "./components/CreateOrderForm";
import SelectOrderForm from "./components/SelectOrderForm";
import SelectedOrderBox from "./components/components/SelectedOrderBox";


export default function UserPage() {
    const dispatch = useDispatch();
    const casteOptions = [{value: 'Alpha', label: 'Альфа'},
        {value: 'Beta', label: 'Бета'},
        {value: 'Gamma', label: 'Гамма'},
        {value: 'Delta', label: 'Дельта'},
        {value: 'Epsilon', label: 'Эпсилон'}];
    const futureJobTypeOptions = useSelector(state => state.order.futureJobTypes);
    useEffect(() => {
        dispatch(getUserInfo());
        dispatch(getFutureJobTypes());
    }, []);

    return (
        <Flex direction="column">
            <MainHeader/>
            <Flex w="full" align="center" justifyContent="left" alignItems="stretch" flex={1}
                  p={5}>
                <Flex direction="column" width="25%" alignItems="stretch">
                    <CreateOrderForm casteOptions={casteOptions} futureJobTypeOptions={futureJobTypeOptions}/>
                    <SelectOrderForm casteOptions={casteOptions} futureJobTypeOptions={futureJobTypeOptions}/>
                </Flex>
                <Flex direction="column" bg="black" w="100%" ml={5}>
                    <SelectedOrderBox/>
                </Flex>
            </Flex>
        </Flex>
    );
}