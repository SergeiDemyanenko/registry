import React from 'react';
import { Reports } from './Reports';
import { Route } from 'react-router-dom';

export class Directory extends React.Component{
    render(){
        return (
            <div>
                <p>This is "Справочники"</p>
                <Route path="/directory/nested1"/>
            </div>
        )
    }
}
