import {createStore, combineReducers} from 'redux';
import userReducer from './reducers/userReducer';
import appReducer from './reducers/appReducer';
import directoryReducer from './reducers/directoryReducer';

export default createStore(
    combineReducers({userReducer, appReducer, directoryReducer}), 
    //checks if Redux devtools are installed in browser
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);
