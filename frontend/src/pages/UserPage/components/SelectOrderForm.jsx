import {Box, FormControl, FormLabel} from "@chakra-ui/react";
import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import * as thunks from "../../../redux/thunks";
import Select from "react-select";
import CurrentOrderBox from "./components/CurrentOrderBox";
import {changeCurrentOrder} from "../../../redux/actions";


export default function SelectOrderForm({casteOptions}) {
    const dispatch = useDispatch();
    const currentOrder = useSelector(state => state.order.currentOrder);
    // const orders = useSelector(state => state.order.orders);
    const orders = [{
        "id": 1,
        "humanNumber": 14,
        "caste": "Alpha",
        "futureJobTypes": ["high-temp", "low-oxxxy", "low-temp", "high-oxxxy"]
    }, {"id": 2, "humanNumber": 88, "caste": "Epsilon", "futureJobTypes": ["high-temp", "low-oxxxy"]}, {
        "id": 3,
        "humanNumber": 1,
        "caste": "Beta",
        "futureJobTypes": []
    }, {"id": 4, "humanNumber": 15, "caste": "Alpha", "futureJobTypes": ["low-oxxxy"]}, {
        "id": 5,
        "humanNumber": 13,
        "caste": "Beta",
        "futureJobTypes": ["low-temp"]
    }, {"id": 6, "humanNumber": 66, "caste": "Delta", "futureJobTypes": ["high-temp"]}, {
        "id": 7,
        "humanNumber": 88,
        "caste": "Epsilon",
        "futureJobTypes": []
    }, {"id": 8, "humanNumber": 14, "caste": "Alpha", "futureJobTypes": []}, {
        "id": 9,
        "humanNumber": 1488,
        "caste": "Epsilon",
        "futureJobTypes": ["high-temp", "low-oxxxy"]
    }, {"id": 10, "humanNumber": 15, "caste": "Epsilon", "futureJobTypes": []}];

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
            <CurrentOrderBox casteOptions={casteOptions} currentOrder={currentOrder}/>
            <form>
                <FormControl my={6}>
                    <FormLabel>Посмотреть заказ</FormLabel>
                    <Select onChange={e => {
                        dispatch(changeCurrentOrder(orders.find(o => o.id === e.value)))
                    }}
                            placeholder="Выберите касту"
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