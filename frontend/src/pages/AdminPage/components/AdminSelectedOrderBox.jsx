import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../../redux/thunks";
import {Box, Heading, Text} from "@chakra-ui/react";
import AdminOvumTable from "./components/AdminOvumTable";
import UpdateOvumForm from "./components/UpdateOvumForm";

export default function AdminSelectedOrderBox() {
    const dispatch = useDispatch();
    const currentOrder = useSelector(state => state.predeterminer.currentOrder);
    const ovumList = useSelector(state => state.predeterminer.ovumByOrder);

    useEffect(() => {
        if (currentOrder.id !== null && currentOrder.processing === true)
            dispatch(thunks.getOvumByOrderId(currentOrder.id));
    }, [currentOrder]);

    return (
        <Box py={3} px={10} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%" h="100%">
            {currentOrder.id !== null ? <>
                    <Heading textAlign="center" size="lg" mb={8}>
                        {`Заказ №${currentOrder.id}: ${currentOrder.humanNumber} человек касты ${currentOrder.caste}`}
                    </Heading>
                    {(ovumList.length !== 0 && currentOrder.processing === true)
                        ?
                        <>
                            <AdminOvumTable ovumList={ovumList}/>
                            <UpdateOvumForm ovumList={ovumList}/>
                        </>
                        : <Text>У данного заказа нет яйцеклеток</Text>}
                </>
                : <Heading textAlign="center" mt={3} size="lg" mb={8}>Заказ не выбран</Heading>}

        </Box>
    )
}