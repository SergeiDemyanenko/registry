import React from 'react';
import { Route } from 'react-router-dom';

export class Person extends React.Component{
    render(){
        return (
            <div>
                <p>This is "Физические лица"</p>
                <Route path="/directory/person"/>
            </div>
        )
    }
}
