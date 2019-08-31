import React from 'react';
import axios  from 'axios';
import {connect} from 'react-redux';
import CircularProgress from '@material-ui/core/CircularProgress';
import personStyles from '../../styles/Person.styles';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import DynamicTable from '../DynamicTable';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Input from '@material-ui/core/Input';


class Person extends React.Component{
  constructor(props){
    super(props);
    this.state = {open: false};
  }
    render(){
    const loadingIndicator = (<div className={this.props.classes.loadingIndicatorContainer}>
                                <CircularProgress/>
                              </div>);
        return (
            <div>
               <Button 
                  variant="contained" 
                  color="primary" 
                  className={this.props.classes.button} 
                  onClick={() => this.toggleDialog()}>
                Добавить
              </Button>
        <Dialog
          open={this.state.open}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
        <DialogTitle id="alert-dialog-title">{"Добавить физическое лицо"}</DialogTitle>
        <DialogContent>
        <div className={this.props.classes.container}>
          <Input
            className={this.props.classes.inputInsideDialog}
            inputProps={{
              'aria-label': 'description',
            }}
          />
          </div>
          <DialogContentText id="alert-dialog-description">
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button variant="contained" color="secondary" 
          className={this.props.classes.button}
          onClick={() => this.toggleDialog()}>
            Отменить
          </Button>
          <Button variant="contained" color="primary" className={this.props.classes.button}>
            Подтвердить
          </Button>
        </DialogActions>
      </Dialog>
              <Button variant="contained" color="secondary" className={this.props.classes.button}>
                Удалить 
              </Button>
              <div className= {this.props.classes.tableContainer}>
                {this.props.directory.peopleLoading ? loadingIndicator : <DynamicTable columns={this.props.directory.columns} rows={this.props.directory.people}/>}
              </div>
            </div>
        )
    }

    toggleDialog(){
      this.setState(state => ({
        open: !state.open
      }))
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