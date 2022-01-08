import React, {useEffect} from "react";
import Flex from "@chakra-ui/core/dist/Flex";
import MainHeader from "../../components/MainHeader";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../redux/thunks";
import SelectPredeterminerForm from "./components/SelectPredeterminerForm";
import AdminSelectedOrderBox from "./components/AdminSelectedOrderBox";


export default function AdminPage() {
    const dispatch = useDispatch();
    const predeterminers = useSelector(state => state.predeterminer.predeterminers);

    useEffect(() => {
        dispatch(thunks.getUserInfo());
        dispatch(thunks.getCastes());
        dispatch(thunks.getFutureJobTypes());
        dispatch(thunks.getPredeterminers());
        dispatch(thunks.getFreeOvumCount());
    }, []);

    return (
        <Flex direction="column">
            <MainHeader/>
            <Flex w="full" align="center" justifyContent="left" alignItems="stretch" flex={1}
                  p={5}>
                <Flex direction="column" width="25%" alignItems="stretch">
                    <SelectPredeterminerForm predeterminers={predeterminers}/>
                </Flex>
                <Flex direction="column" w="100%" ml={5}>
                    <AdminSelectedOrderBox/>
                </Flex>
            </Flex>
        </Flex>
    );
}