import React from 'react';

import {BrowserRouter, Routes} from "react-router-dom";
import {Route} from "react-router";
import UserPage from "../pages/UserPage/UserPage";
import SignInPage from "../pages/AuthPages/SignInPage";
import SignUpPage from "../pages/AuthPages/SignUpPage";
import AdminPage from "../pages/AdminPage/AdminPage";
import VolunteerPage from "../pages/VolunteerPage/VolunteerPage";


export function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/auth/signin/*' element={<SignInPage/>}/>
                <Route path='/auth/signup/*' element={<SignUpPage/>}/>
                <Route path='/user' element={<UserPage/>}/>
                <Route path='/volunteer' element={<VolunteerPage/>}/>
                <Route path='/reviewer' element={<UserPage/>}/>
                <Route path='/admin' element={<AdminPage/>}/>
            </Routes>
        </BrowserRouter>
    )
}