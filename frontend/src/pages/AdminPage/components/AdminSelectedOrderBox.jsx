import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../../redux/thunks";
import {Box, Heading, Text} from "@chakra-ui/react";
import AdminOvumTable from "./components/AdminOvumTable";
import UpdateOvumForm from "./components/UpdateOvumForm";
import MovingTable from "./components/MovingTable";
import UsingTable from "./components/UsingTable";
import AddingTable from "./components/AddingTable";

export default function AdminSelectedOrderBox() {
    const dispatch = useDispatch();
    const currentOrder = useSelector(state => state.order.currentOrder);
    const ovumList = useSelector(state => state.ovum.ovumByOrder);
    const movingList = useSelector(state => state.history.movingList);
    const usingList = useSelector(state => state.history.usingList);
    const addingList = useSelector(state => state.history.addingList);

    useEffect(() => {
        if (currentOrder.id !== null && currentOrder.processing === true) {
            dispatch(thunks.getOvumByOrderId(currentOrder.id));
            dispatch(thunks.getMovingListByOrderId(currentOrder.id));
            dispatch(thunks.getUsingListByOrderId(currentOrder.id));
            dispatch(thunks.getAddingListByOrderId(currentOrder.id));

        }
    }, [currentOrder, dispatch]);

    return (
        <Box py={3} px={10} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%" h="100%">
            {currentOrder.id !== null ? <>
                    <Heading textAlign="center" size="lg" mb={8}>
                        {`Заказ №${currentOrder.id}: ${currentOrder.humanNumber} человек касты ${currentOrder.caste}`}
                    </Heading>
                    {currentOrder.processing === true
                        ?
                        <>
                            {ovumList.length !== 0 && <AdminOvumTable ovumList={ovumList}/>}
                            {ovumList.length !== 0 && <UpdateOvumForm ovumList={ovumList}/>}
                            {movingList.length !== 0 && <MovingTable movingList={movingList}/>}
                            {usingList.length !== 0 && <UsingTable usingList={usingList}/>}
                            {addingList.length !== 0 && <AddingTable addingList={addingList}/>}
                        </>
                        : <Text>Данный заказ не выполняется</Text>}
                </>
                : <Heading textAlign="center" mt={3} size="lg" mb={8}>Заказ не выбран</Heading>}
        </Box>
    )
}