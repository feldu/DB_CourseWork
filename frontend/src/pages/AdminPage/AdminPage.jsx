import React, {useEffect} from "react";
import Flex from "@chakra-ui/core/dist/Flex";
import MainHeader from "../../components/MainHeader";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../redux/thunks";
import SelectedOrderBox from "../UserPage/components/components/SelectedOrderBox";
import SelectPredeterminerForm from "./components/SelectPredeterminerForm";


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
                <Flex direction="column" bg="black" w="100%" ml={5}>
                    <SelectedOrderBox/>
                </Flex>
            </Flex>
        </Flex>
    );
}