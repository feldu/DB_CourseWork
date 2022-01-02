import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import {ChakraProvider} from '@chakra-ui/react'
import {composeWithDevTools} from 'redux-devtools-extension';
import thunk from "redux-thunk";
import {applyMiddleware, createStore} from "redux";
import {rootReducer} from "./redux/reducers/rootReducer";
import {Provider} from "react-redux";

const store = createStore(rootReducer, composeWithDevTools(applyMiddleware(thunk)));

ReactDOM.render(
    <ChakraProvider>
        <Provider store={store}>
            <App/>
        </Provider>
    </ChakraProvider>,
    document.getElementById('root')
);
