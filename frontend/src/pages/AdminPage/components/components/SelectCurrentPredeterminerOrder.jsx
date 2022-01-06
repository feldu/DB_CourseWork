import {Box} from "@chakra-ui/react";
import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import InputSelect from "../../../../components/InputSelect";
import * as actions from "../../../../redux/actions";
import Text from "@chakra-ui/core/dist/Text";


export default function SelectCurrentPredeterminerOrder() {
    const dispatch = useDispatch();
    const orders = useSelector(state => state.predeterminer.orders);
    const [currentOrderOptions, setCurrentOrderOptions] = useState([]);


    useEffect(() => {
        setCurrentOrderOptions(orders.map(order => ({
            value: order.id,
            label: `№${order.id}: ${order.humanNumber} шт. касты ${order.caste}`
        })));
        console.log("for options: " + orders);
    }, [orders]);

    return (
        <Box mt={5} w="100%" h="100%">
            <form>
                {!orders.length ?
                    <Text textAlign="center">У данного Предопределителя нет действующих заказов</Text>
                    :
                    < InputSelect
                        label={"Сделал заказы:"}
                        onChangeHandler={e => dispatch(actions.changeCurrentPredeterminerOrder(orders.find(o => o.id === e.value)))}
                        placeholder={"Выберите заказ"}
                        options={currentOrderOptions}
                    />
                }
            </form>
        </Box>
    );
}