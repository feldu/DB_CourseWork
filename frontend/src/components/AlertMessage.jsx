import {Alert, AlertDescription, AlertIcon, Box} from "@chakra-ui/react";
import React from "react";

export default function AlertMessage({message, status}) {
    return (
        <Box my={4} align="center">
            <Alert status={status} borderRadius={4}>
                <AlertIcon/>
                <AlertDescription>{message}</AlertDescription>
            </Alert>
        </Box>
    );
}