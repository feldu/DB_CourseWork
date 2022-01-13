import React, {useEffect} from "react";
import Flex from "@chakra-ui/core/dist/Flex";
import MainHeader from "../../components/MainHeader";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../redux/thunks";
import CreateOrderForm from "./components/CreateOrderForm";
import SelectOrderForm from "./components/SelectOrderForm";
import UserSelectedOrderBox from "./components/UserSelectedOrderBox";


export default function UserPage() {
    const dispatch = useDispatch();
    const casteOptions = useSelector(state => state.order.castes);
    const futureJobTypeOptions = useSelector(state => state.order.futureJobTypes);

    useEffect(() => {
        dispatch(thunks.getUserInfo());
        dispatch(thunks.getFutureJobTypes());
        dispatch(thunks.getCastes());
    }, [dispatch]);

    return (
        <Flex direction="column">
            <MainHeader/>
            <Flex w="full" align="center" justifyContent="left" alignItems="stretch" flex={1}
                  p={5}>
                <Flex direction="column" width="25%" alignItems="stretch">
                    <CreateOrderForm casteOptions={casteOptions} futureJobTypeOptions={futureJobTypeOptions}/>
                    <SelectOrderForm casteOptions={casteOptions} futureJobTypeOptions={futureJobTypeOptions}/>
                </Flex>
                <Flex direction="column" w="100%" ml={5}>
                    <UserSelectedOrderBox/>
                </Flex>
            </Flex>
        </Flex>
    );
}