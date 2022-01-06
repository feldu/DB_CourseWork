import {Box, Heading} from "@chakra-ui/react";
import Text from "@chakra-ui/core/dist/Text";
import React from "react";

export default function CurrentPredeterminerBox({currentPredeterminer}) {
    return (
        <Box textAlign="center">
            <Heading my={3} size="lg">Предопределители</Heading>
            {!currentPredeterminer.id ? (
                    <Box>
                        <Text>Выберите Предопределителя из списка.</Text>
                    </Box>)
                :
                (<Box>
                    <Text mb={2}>Текущий Предопределитель <b>№{currentPredeterminer.id}:</b></Text>
                    <Text><b>ФИО: </b>{currentPredeterminer.fullname}.</Text>
                </Box>)}
        </Box>

    );
}