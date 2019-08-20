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
    const loadingIndicator = (<div className={this.props.classes.loadingIndicatorContainer}>
                                <CircularProgress/>
                              </div>);
    const table = (<Table>
    <TableHead>
    <TableRow>
      {/* TODO: Hide ID_PERSON column */}
        {this.props.directory.columns.map(column => (
          <TableCell>{column}</TableCell>
        ))}
    </TableRow>
    </TableHead>
    <TableBody>
      {/* TODO: Hide ID_PERSON column */}
        {this.props.directory.people.map((person, personIndex) => (
            <TableRow key={personIndex}>
              {this.props.directory.columns.map((column, columnIndex) => (
                <TableCell>{person[columnIndex]}</TableCell>
              ))}
            </TableRow>
        ))}
    </TableBody>
</Table>)
        return (
            <div>
                <p>This is "Физические лица"</p>
                <div className= {this.props.classes.tableContainer}>
                    {this.props.directory.peopleLoading ? loadingIndicator : table}
                    <Route path="/directory/person"/>
                </div>
            </div>
        )
    }

    componentWillMount(){
        this.props.setPeopleLoading(true);
        this.fetchPeople().then((response) => {
            this.props.setPeopleLoading(false);
            this.props.setColumns(response.data.HEAD);
            this.props.setPeople(response.data.DATA);
        });
    }

    fetchPeople() {
        return axios({
            method: 'get',
            url: '/api/directory/person',
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
      },
      setColumns: (columns) => {
        dispatch({
          type: 'SET_COLUMNS',
          payload: columns
        })
      }
    }
  }

export default connect(mapStateToProps,mapDispatchToProps)(withStyles(personStyles)(Person));