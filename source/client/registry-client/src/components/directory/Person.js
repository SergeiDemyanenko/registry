import React from 'react';
import { Route } from 'react-router-dom';
import axios  from 'axios';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import {connect} from 'react-redux';
import CircularProgress from '@material-ui/core/CircularProgress';
import personStyles from '../../styles/Person.styles';
import { withStyles } from '@material-ui/core/styles';


class Person extends React.Component{
    render(){
    const loadingIndicator = <CircularProgress/>;

    const table = (<Table>
    <TableHead>
    <TableRow>
        <TableCell>Last Name</TableCell>
        <TableCell align="right">Name</TableCell>
        <TableCell align="right">Patronymic</TableCell>
        <TableCell align="right">Birth Date</TableCell>
        <TableCell align="right">Phone</TableCell>
        <TableCell align="right">Pass Ser</TableCell>
        <TableCell align="right">Pass Num</TableCell>
        <TableCell align="right">Pass Issue</TableCell>
        <TableCell align="right">Pass Date</TableCell>
        <TableCell align="right">Pass Issue Code</TableCell>
        <TableCell align="right">Pass Address</TableCell>
    </TableRow>
    </TableHead>
    <TableBody>
        {this.props.directory.people.map((row, index) => (
            // add appropriate names to rows
            <TableRow key={index}>
                <TableCell component="th" scope="row">
                    {row.SNAME}
                </TableCell>
                <TableCell align="right">{row.NAME}</TableCell>
                <TableCell align="right">{row.PNAME}</TableCell>
                <TableCell align="right">{row.BIRTH_DATE}</TableCell>
                <TableCell align="right">{row.PHONE}</TableCell>
                <TableCell>{row.PASS_SERIES}</TableCell>
                <TableCell>{row.PASS_NUMBER}</TableCell>
                <TableCell align="right">{row.PASS_ISSUE}</TableCell>
                <TableCell>{row.PASS_DATE}</TableCell>
                <TableCell align="right">{row.PASS_ISSUE_CODE}</TableCell>
                <TableCell align="right">{row.PASS_ARRDESS}</TableCell>
            </TableRow>
        ))}
    </TableBody>
</Table>)
        return (
            <div>
                <p>This is "Физические лица"</p>
                <div className={this.props.classes.tableContainer}>
                    {this.props.directory.peopleLoading ? loadingIndicator : table}
                    <Route path="/directory/person"/>
                </div>
            </div>
        )
    }

    componentWillMount(){
        this.props.setPeopleLoading(true);
        // setPeopleLoading to true by using function created below 'setPeopleLoading'
        this.fetchPeople().then((response) => {
        // setPeopleLoading to false by using function created below 'setPeopleLoading'
            this.props.setPeopleLoading(false);
            this.props.setPeople(response.data);
        })
    }

    fetchPeople() {
        return axios({
            method: 'get',
            url: 'http://localhost:8080/directory/person',
            responseType: 'json'
          });
    }
}
const mapStateToProps = (state) => {
    return {
      directory: state.directoryReducer
    }
  }
  const mapDispatchToProps = (dispatch) => {
    return {
      setPeople: (people) => {
        dispatch({
          type: 'SET_PEOPLE',
          payload: people,
        })
      },
      setPeopleLoading: (isLoading) => {
          dispatch({
            type: 'SET_PEOPLE_LOADING',
            payload: isLoading
          })
      }
      // Add similar to setPeople function that takes boolean true or false. Name it setPeopleLoading
      // This function will dispatch action with type SET_PEOPLE_LOADING and payload will be true or false 
      // based on what has been passed into the setPeopleLoading function

    }
  }

export default connect(mapStateToProps,mapDispatchToProps)(withStyles(personStyles)(Person));
