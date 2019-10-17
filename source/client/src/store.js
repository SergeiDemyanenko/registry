import { createStore, combineReducers } from 'redux';
import appReducer from './reducers/appReducer';
import dataReducer from './reducers/dataReducer';

export default createStore(
	combineReducers({ appReducer, dataReducer }),
	//checks if Redux devtools are installed in browser
	window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);
