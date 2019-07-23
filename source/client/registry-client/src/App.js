import React from 'react';
import './App.css';
import {Home} from './components/Home';
import {Header} from './components/Header';
import {connect} from 'react-redux';
import Button from '@material-ui/core/Button';

class App extends React.Component {
  render(){
    return (
      <div className="App">
        {this.props.user.firstName}
        <Header header={'Header'}/> 
        <Home name={this.props.user.firstName}/>
        <Button variant="contained" color="primary" onClick={() => this.props.setName('meh')}>Change Name redux</Button>
      </div>
      );
    } 
}

const mapStateToProps = (state) => {
  return {
    user: state.userReducer
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    setName: (name) => {
      dispatch({
        type: 'SET_NAME',
        payload: name,
      })
    }
  }
}

export default connect(mapStateToProps,mapDispatchToProps)(App);