import {Box, Button, Heading, Text} from "@chakra-ui/react";
import React, {useEffect} from "react";
import {Flex} from "@chakra-ui/core";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../../../../redux/thunks";


export default function StageStarterForms() {
    const dispatch = useDispatch();
    const currentOrder = useSelector(state => state.order.currentOrder);
    const ovumList = useSelector(state => state.ovum.ovumByOrder);

    const firstComplete = ovumList.filter(o => o.fertilizationTime !== null).length === ovumList.length;
    const secondComplete = ovumList.length === currentOrder.humanNumber || ["Alpha", "Beta"].includes(currentOrder.caste);
    const thirdComplete = ovumList.length !== 0 && ovumList.filter(o => o.babyTime !== null).length === ovumList.length;
    useEffect(() => {
        if (ovumList.length !== 0 && currentOrder.id !== null)
            if (ovumList.length > currentOrder.humanNumber)
                dispatch(thunks.removeExtraOvum(currentOrder.id));
    }, [ovumList, currentOrder, dispatch]);
    return (
        <Box mt={5} w="100%" h="100%">
            <Flex justifyContent="center" flexDirection="column" alignItems="center">
                <Heading textAlign="center" size="md" my={4}>Этап 1: "Оплодотворение"</Heading>
                <form>
                    {ovumList.filter(o => o.fertilizationTime === null).length !== ovumList.length ?
                        (firstComplete ?
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
                        (!firstComplete ?
                            <Text>Сначала завершите выполнение первого этапа.</Text>
                            :
                            (secondComplete ?
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
                    {!secondComplete ?
                        <Text>Сначала завершите выполнение второго этапа.</Text>
                        :
                        (thirdComplete ? <Text>Третий этап завершён. Заказ выполнен.</Text>
                            :
                            <Button colorScheme='teal' variant='solid' type="submit" onClick={e => {
                                e.preventDefault();
                                dispatch(thunks.startThirdStep(currentOrder.id));
                            }}>
                                Запустить
                            </Button>)}
                </form>
                {thirdComplete && <Text mt={6} mb={4}><b>Удалить информацию о заказе?</b></Text>}
                <form>
                    {thirdComplete &&
                    <Button colorScheme='red' variant='solid' type="submit" onClick={e => {
                        e.preventDefault();
                        dispatch(thunks.removeOrderById(currentOrder.id));
                    }}>
                        Удалить
                    </Button>}
                </form>
            </Flex>
        </Box>
    );
}