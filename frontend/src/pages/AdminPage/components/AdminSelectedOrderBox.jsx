import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../../redux/thunks";
import {Box, Text} from "@chakra-ui/core";
import AdminOvumTable from "./components/AdminOvumTable";

export default function AdminSelectedOrderBox() {
    const dispatch = useDispatch();
    const currentOrder = useSelector(state => state.predeterminer.currentOrder);

    useEffect(() => {
        if (currentOrder.id !== null && currentOrder.processing === true)
            dispatch(thunks.getOvumByOrderId(currentOrder.id));
    }, [currentOrder]);

    return (
        <Box py={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%" h="100%">
            <Text>Order: {JSON.stringify(currentOrder, null, 2)}</Text>
            <AdminOvumTable/>
        </Box>
    )
}