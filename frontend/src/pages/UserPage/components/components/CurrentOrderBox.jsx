import {Box, Heading} from "@chakra-ui/react";
import Text from "@chakra-ui/core/dist/Text";
import React from "react";

export default function CurrentOrderBox({casteOptions, currentOrder, futureJobTypeOptions}) {
    return (
        <Box textAlign="center">
            <Heading my={3} size="lg">Ваш заказ</Heading>
            {!currentOrder.id ? (
                    <Box>
                        <Text>Сейчас ни один заказ не выбран.</Text>
                        <Text>Все доступные Вам заказы в списке ниже.</Text>
                    </Box>)
                :
                (<Box>
                    <Text><b>Текущий заказ №{currentOrder.id}:</b></Text>
                    <Text>Количество человек: {currentOrder.humanNumber} шт.</Text>
                    <Text>Каста: {casteOptions.find(caste => caste.value.includes(currentOrder.caste)).label}</Text>
                    <Text>
                        {"Доп. требования: "}
                        {currentOrder.futureJobTypes.length !== 0 ?
                            currentOrder.futureJobTypes.map(oType => futureJobTypeOptions.find(x => x.value.includes(oType)).label).toString() :
                            "Нет"}
                    </Text>
                </Box>)}
        </Box>
    );
}