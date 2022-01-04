import {Box, FormControl, FormLabel} from "@chakra-ui/react";
import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../../redux/thunks";
import Select from "react-select";
import CurrentOrderBox from "./components/CurrentOrderBox";
import {changeCurrentOrder} from "../../../redux/actions";


export default function SelectOrderForm({casteOptions, futureJobTypeOptions}) {
    const dispatch = useDispatch();
    const currentOrder = useSelector(state => state.order.currentOrder);
    const orders = useSelector(state => state.order.orders);

    const currentOrderOptions = orders.map(order => {
        return {
            value: order.id,
            label: `№${order.id}: ${order.humanNumber} шт. касты ${casteOptions.find(caste => caste.value.includes(order.caste)).label}`
        }
    });
    useEffect(() => {
        dispatch(thunks.getOrders());
    }, []);
    return (
        <Box mt={5} p={2} px={5} borderWidth={1} borderRadius={14} boxShadow="lg" w="100%" h="100%">
            <CurrentOrderBox casteOptions={casteOptions} currentOrder={currentOrder}
                             futureJobTypeOptions={futureJobTypeOptions}/>
            <form>
                <FormControl my={6}>
                    <FormLabel>Посмотреть заказ</FormLabel>
                    <Select onChange={e => {
                        dispatch(changeCurrentOrder(orders.find(o => o.id === e.value)))
                    }}
                            placeholder="Выберите заказ"
                            name="colors"
                            options={currentOrderOptions}
                            className="basic-single"
                            classNamePrefix="select"
                    />
                </FormControl>
            </form>
        </Box>
    );
}