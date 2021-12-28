import React from "react";
import Text from "@chakra-ui/core/dist/Text";
import Flex from "@chakra-ui/core/dist/Flex";
import MainHeader from "../components/MainHeader";


export default function AdminPage() {
    return (
        <Flex direction="column">
            <MainHeader/>
            <Text>Я админ блядь.</Text>
        </Flex>
    );
}