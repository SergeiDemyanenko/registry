import {createStore, combineReducers} from 'redux';
import userReducer from './reducers/userReducer';

export default createStore(
    combineReducers({userReducer}), 
    //checks if Redux devtools are installed in browser
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);
