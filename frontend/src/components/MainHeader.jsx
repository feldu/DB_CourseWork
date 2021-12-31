import React from "react";
import {Button, Flex} from "@chakra-ui/react";
import * as thunks from "../redux/thunks";
import {useDispatch} from "react-redux";

export default function MainHeader() {
    const dispatch = useDispatch();
    const logout = () => {
        dispatch(thunks.logout());
    };
    //todo: add user info to header
    return (
        <Flex
            direction="row-reverse"
            as="nav"
            align="center"
            justify="space-between"
            wrap="wrap"
            padding={2}
            bg="teal.300"
            color="white"
        >
            <Button
                variant="outline"
                _hover={{bg: "teal.400", borderColor: "teal.400"}}
                onClick={logout}
                mr="2"
            >
                Выйти
            </Button>
        </Flex>
    );
}