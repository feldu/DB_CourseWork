import {Box} from "@chakra-ui/react";
import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../../redux/thunks";
import CurrentOrderBox from "./components/CurrentOrderBox";
import {changeCurrentOrder} from "../../../redux/actions";
import InputSelect from "../../../components/InputSelect";


export default function SelectOrderForm({casteOptions, futureJobTypeOptions}) {
    const dispatch = useDispatch();
    const currentOrder = useSelector(state => state.order.currentOrder);
    const orders = useSelector(state => state.order.orders);
    const [currentOrderOptions, setCurrentOrderOptions] = useState([]);

    useEffect(() => {
        dispatch(thunks.getOrders());
    }, []);

    useEffect(() => {
        setCurrentOrderOptions(orders.map(order => ({
            value: order.id,
            label: `№${order.id}: ${order.humanNumber} шт. касты ${casteOptions.find(caste => caste.value.includes(order.caste)).label}`
        })))
    }, [casteOptions]);

    return (
        <Box mt={5} p={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%" h="100%">
            <CurrentOrderBox casteOptions={casteOptions} currentOrder={currentOrder}
                             futureJobTypeOptions={futureJobTypeOptions}/>
            <form>
                <InputSelect
                    label={"Посмотреть заказ"}
                    onChangeHandler={e => dispatch(changeCurrentOrder(orders.find(o => o.id === e.value)))}
                    placeholder={"Выберите заказ"}
                    options={currentOrderOptions}
                />
            </form>
        </Box>
    );
}