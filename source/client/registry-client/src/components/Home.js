import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';

export class Home extends React.Component{
    render() {
        return (
            <div>
                <p>Your name is {this.props.name}</p>
                <p>Your age is {this.props.age}</p>
            </div>
        )
    }

}