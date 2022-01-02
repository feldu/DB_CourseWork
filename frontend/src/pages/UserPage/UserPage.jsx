import React, {useEffect} from "react";
import Text from "@chakra-ui/core/dist/Text";
import Flex from "@chakra-ui/core/dist/Flex";
import MainHeader from "../../components/MainHeader";
import {useDispatch} from "react-redux";
import {getUserInfo} from "../../redux/thunks";


export default function UserPage() {
    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(getUserInfo());
    }, []);

    return (
        <Flex direction="column">
            <MainHeader/>
            <Text>Я юзер блядь.</Text>
        </Flex>
    );
}