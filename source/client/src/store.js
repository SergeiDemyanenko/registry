import {createStore, combineReducers} from 'redux';
import appReducer from './reducers/appReducer';
import directoryReducer from './reducers/directoryReducer';

export default createStore(
    combineReducers({appReducer, directoryReducer}), 
    //checks if Redux devtools are installed in browser
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);
