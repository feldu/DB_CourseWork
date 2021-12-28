import React from "react";
import Text from "@chakra-ui/core/dist/Text";
import Flex from "@chakra-ui/core/dist/Flex";
import MainHeader from "../components/MainHeader";


export default function UserPage() {
    return (
        <Flex direction="column">
            <MainHeader/>
            <Text>Я юзер блядь.</Text>
        </Flex>
    );
}