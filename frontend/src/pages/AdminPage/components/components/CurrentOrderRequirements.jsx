import {Box} from "@chakra-ui/react";
import Text from "@chakra-ui/core/dist/Text";
import React from "react";
import {useSelector} from "react-redux";
import {calculateOvumCount} from "../../../../utils/calculations";

export default function CurrentOrderRequirements() {
    const currentOrder = useSelector(state => state.predeterminer.currentOrder);
    const freeOvumCount = useSelector(state => state.predeterminer.freeOvumCount);
    return (
        <Box textAlign="center">
            <Box>
                <Text mt={4}>Для заказа <b>№{currentOrder.id}</b> требуется:</Text>
                <Text mt={2}><b>{currentOrder.humanNumber}</b> человек касты <b>{currentOrder.caste}</b>:</Text>
                <Text mt={4}>Требуется
                    минимум <b>{calculateOvumCount(currentOrder.humanNumber, currentOrder.caste)}</b> яйцеклеток:</Text>
                <Text mt={2}>Доступно яйцеклеток: <b>{freeOvumCount}</b>:</Text>
            </Box>
        </Box>

    );
}