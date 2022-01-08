import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../../redux/thunks";
import {Box, Text} from "@chakra-ui/core";


export default function AdminSelectedOrderBox() {
    const dispatch = useDispatch();
    const ovumList = useSelector(state => state.predeterminer.ovumByOrder);
    const currentOrder = useSelector(state => state.predeterminer.currentOrder);

    useEffect(() => {
        if (currentOrder.id !== null && currentOrder.processing === true)
            dispatch(thunks.getOvumByOrderId(currentOrder.id));
    }, [currentOrder]);

    return (
        <Box py={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%" h="100%">
            <Text>Order: {JSON.stringify(currentOrder, null, 2)}</Text>
            <Text>OvumList: {ovumList.map(o => <Text mt={5}>{JSON.stringify(o, null, 2)}</Text>)}</Text>
        </Box>
    )
}