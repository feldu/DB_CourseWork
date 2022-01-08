import {Box, Button} from "@chakra-ui/react";
import Text from "@chakra-ui/core/dist/Text";
import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {calculateOvumCount} from "../../../../utils/calculations";
import * as thunks from "../../../../redux/thunks";
import AlertMessage from "../../../../components/AlertMessage";

export default function CurrentOrderRequirements() {
    const dispatch = useDispatch();
    const currentOrder = useSelector(state => state.predeterminer.currentOrder);
    const freeOvumCount = useSelector(state => state.predeterminer.freeOvumCount);
    const bindInfo = useSelector(state => state.message.messageInfo);
    const neededOvumCount = calculateOvumCount(currentOrder.humanNumber, currentOrder.caste);

    const onClickHandler = e => {
        e.preventDefault();
        dispatch(thunks.bindFreeOvumToOrder(currentOrder.id, neededOvumCount));
    };
    return (
        <Box>
            {currentOrder.processing ?
                <Text textAlign="center">Заказ выполняется...</Text>
                :
                <Box textAlign="left">
                    <Box>
                        <Text mt={4}>Для заказа <b>№{currentOrder.id}</b> требуется:</Text>
                        <Text mt={2}><b>{currentOrder.humanNumber}</b> человек касты <b>{currentOrder.caste}</b></Text>
                        <Text mt={4}>Для этого нужно минимум <b>{neededOvumCount}</b> яйцеклеток.</Text>
                        <Text textAlign="center" mt={2}>Доступно яйцеклеток: <b>{freeOvumCount}</b>.</Text>
                    </Box>
                    {neededOvumCount > freeOvumCount ?
                        <Text textAlign="center" mt={5}>Не хватает свободных яйцеклеток для выполнения
                            заказа</Text>
                        :
                        <Box textAlign="center" mt={5}>
                            <form>
                                <Button colorScheme='teal' variant='solid' type="submit" onClick={onClickHandler}>
                                    Начать выполнение заказа
                                </Button>
                            </form>
                        </Box>}
                </Box>}
            {bindInfo.message &&
            <AlertMessage message={bindInfo.message} status={bindInfo.isError ? "error" : "success"}/>}
        </Box>);
}