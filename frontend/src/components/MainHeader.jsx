import React from "react";
import {Box, Button, Flex, Text} from "@chakra-ui/react";
import * as thunks from "../redux/thunks";
import {useDispatch, useSelector} from "react-redux";

export default function MainHeader() {
    const dispatch = useDispatch();
    const user = useSelector(state => state.authorization.currentUser);

    const logout = () => {
        dispatch(thunks.logout());
    };
    return (
        <Flex
            direction="row"
            as="nav"
            align="center"
            justify="space-between"
            wrap="wrap"
            padding={2}
            bg="teal.300"
            color="white"
        >
            <Box>
                <Text>Пользователь: {user.username}</Text>
                <Text>Роль: {user.role}</Text>
            </Box>
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