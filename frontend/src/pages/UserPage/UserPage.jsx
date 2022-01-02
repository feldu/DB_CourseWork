import React, {useEffect} from "react";
import Flex from "@chakra-ui/core/dist/Flex";
import MainHeader from "../../components/MainHeader";
import {useDispatch} from "react-redux";
import {getUserInfo} from "../../redux/thunks";
import OrderForm from "./components/OrderForm";


export default function UserPage() {
    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(getUserInfo());
    }, []);

    return (
        <Flex direction="column">
            <MainHeader/>
            <Flex width="full" align="center" justifyContent="left" alignItems="stretch" flex={1}
                  m={5}>
                <OrderForm/>
                {
                    //todo: insert here component with current order
                }
            </Flex>
        </Flex>
    );
}