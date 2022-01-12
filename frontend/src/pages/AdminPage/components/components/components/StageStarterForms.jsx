import {Box, Button, Heading, Text} from "@chakra-ui/react";
import React, {useEffect} from "react";
import {Flex} from "@chakra-ui/core";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../../../../redux/thunks";


export default function StageStarterForms() {
    const dispatch = useDispatch();
    const currentOrder = useSelector(state => state.predeterminer.currentOrder);
    const ovumList = useSelector(state => state.predeterminer.ovumByOrder);
    useEffect(() => {
        if (ovumList.length !== 0 && currentOrder.id !== null)
            if (ovumList.length > currentOrder.humanNumber)
                dispatch(thunks.removeExtraOvum(currentOrder.id));
    }, [ovumList, currentOrder]);
    return (
        <Box mt={5} w="100%" h="100%">
            <Flex justifyContent="center" flexDirection="column" alignItems="center">
                <Heading textAlign="center" size="md" my={4}>Этап 1: "Оплодотворение"</Heading>
                <form>
                    {ovumList.filter(o => o.fertilizationTime === null).length !== ovumList.length ?
                        (ovumList.filter(o => o.fertilizationTime !== null).length === ovumList.length ?
                                <Text>Все яйцеклетки оплодотворены. Первый этап завершён.</Text>
                                :
                                <Text>Есть оплодовторённые яйцеклетки. Не возможно начать первый этап.</Text>
                        )
                        :
                        <Button colorScheme='teal' variant='solid' type="submit" onClick={(e) => {
                            e.preventDefault();
                            dispatch(thunks.startFirstStep(currentOrder.id));
                        }}> Запустить </Button>
                    }
                </form>
                <Heading textAlign="center" size="md" my={4} mt={6}>Этап 2: "Дробление"</Heading>
                <form>
                    {["Alpha", "Beta"].includes(currentOrder.caste) ?
                        <Text>Для каст Альфа и Бета не требуется выполнение второго этапа.</Text>
                        :
                        (ovumList.filter(o => o.fertilizationTime !== null).length !== ovumList.length ?
                            <Text>Сначала завершите выполнение первого этапа.</Text>
                            :
                            (ovumList.length === currentOrder.humanNumber ?
                                <Text>Дробление выполнено. Второй этап завершён.</Text>
                                :
                                <Button colorScheme='teal' variant='solid' type="submit" onClick={e => {
                                    e.preventDefault();
                                    dispatch(thunks.startSecondStep(currentOrder.id));
                                }}> Запустить </Button>))
                    }
                </form>
                <Heading textAlign="center" size="md" my={4} mt={6}>Этап 3: "Набутыливание"</Heading>
                <form>
                    <Button colorScheme='teal' variant='solid' type="submit" onClick={e => {
                    }}>
                        Запустить
                    </Button>
                </form>
            </Flex>
        </Box>
    );
}