import React from "react";
import {Button, Flex, Heading, Text} from "@chakra-ui/react";
import {useNavigate} from "react-router";

export default function AuthHeader({buttonText, path}) {
    let navigate = useNavigate();
    return (
        <Flex
            as="nav"
            align="center"
            justify="space-between"
            wrap="wrap"
            padding={6}
            bg="teal.300"
            color="white"
        >
            <Heading as="h1" size="lg" letterSpacing={"tighter"}>
                <Text>thISIS ANALyze Reality</Text>
            </Heading>
            <Button
                variant="outline"
                _hover={{bg: "teal.400", borderColor: "teal.400"}}
                onClick={() => navigate(path)}
            >
                {buttonText}
            </Button>
        </Flex>
    );
}