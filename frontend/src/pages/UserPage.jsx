import React from "react";
import Text from "@chakra-ui/core/dist/Text";
import Flex from "@chakra-ui/core/dist/Flex";
import UserHeader from "../components/UserHeader";


export default function UserPage() {
    return (
        <Flex direction="column">
            <UserHeader/>
            <Text>Я юзер блядь.</Text>
        </Flex>
    );
}