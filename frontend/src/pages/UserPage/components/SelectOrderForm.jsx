import {Box} from "@chakra-ui/react";
import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../../redux/thunks";
import CurrentOrderBox from "./components/CurrentOrderBox";
import InputSelect from "../../../components/InputSelect";
import * as actions from "../../../redux/actions";


export default function SelectOrderForm({casteOptions, futureJobTypeOptions}) {
    const dispatch = useDispatch();
    const currentOrder = useSelector(state => state.order.currentOrder);
    const orders = useSelector(state => state.order.orders);
    const [currentOrderOptions, setCurrentOrderOptions] = useState([]);

    useEffect(() => {
        dispatch(thunks.getOrders());
    }, [dispatch]);

    useEffect(() => {
        if (casteOptions.length !== 0)
            setCurrentOrderOptions(orders.map(order => ({
                value: order.id,
                label: `№${order.id}: ${order.humanNumber} шт. касты ${casteOptions.find(caste => caste.value.includes(order.caste)).label}`
            })))
    }, [casteOptions, orders]);

    return (
        <Box mt={5} p={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%" h="100%">
            <CurrentOrderBox casteOptions={casteOptions} currentOrder={currentOrder}
                             futureJobTypeOptions={futureJobTypeOptions}/>
            <form>
                <InputSelect
                    label={"Посмотреть заказ"}
                    onChangeHandler={e => dispatch(actions.changeCurrentOrder(orders.find(o => o.id === e.value)))}
                    placeholder={"Выберите заказ"}
                    options={currentOrderOptions}
                />
            </form>
        </Box>
    );
}