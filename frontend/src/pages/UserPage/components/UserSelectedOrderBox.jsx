import {Box} from "@chakra-ui/core";
import React, {useEffect} from "react";
import {Heading, Text} from "@chakra-ui/react";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../../redux/thunks";
import UserOvumTable from "./components/UserOvumTable";
import AlertMessage from "../../../components/AlertMessage";


export default function UserSelectedOrderBox() {
    const dispatch = useDispatch();
    const currentOrder = useSelector(state => state.order.currentOrder);
    const ovumList = useSelector(state => state.ovum.ovumByOrder);
    const isComplete = ovumList.length !== 0 && ovumList.filter(o => o.babyTime !== null).length === ovumList.length;

    useEffect(() => {
        if (currentOrder.id !== null && currentOrder.processing === true) {
            dispatch(thunks.getOvumByOrderId(currentOrder.id));
        }
    }, [currentOrder, dispatch]);

    return (
        <Box py={3} px={10} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%" h="100%">
            {currentOrder.id !== null ? <>
                    <Heading textAlign="center" size="lg" mb={8}>
                        {isComplete ?
                            `Заказ №${currentOrder.id} выполнен!`
                            :
                            `Заказ №${currentOrder.id}: ${currentOrder.humanNumber} человек касты ${currentOrder.caste}`
                        }
                    </Heading>
                    {currentOrder.processing === true
                        ?
                        <Box>
                            {ovumList.length !== 0 && (
                                isComplete ?
                                    <AlertMessage status={"success"}
                                                  message={"Заберите его из пункта выдачи в любое удобное вам время.\n" +
                                                  "Вход в пункт выдачи только в маске и перчатках - это поможет сохранить здоровье вам и окружающим."}/>
                                    :
                                    <UserOvumTable ovumList={ovumList}/>
                            )}
                        </Box>
                        : <Text>Данный заказ не выполняется</Text>}
                </>
                : <Heading textAlign="center" mt={3} size="lg" mb={8}>Заказ не выбран</Heading>}
        </Box>
    )
}