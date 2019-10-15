import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import 'typeface-roboto';
import { Provider } from 'react-redux';
import store from './store';
import { BrowserRouter as Router, withRouter } from 'react-router-dom';
import Login from './components/Login';
import axios from 'axios';

const AppWithRouter = withRouter(props => <App {...props} />);

const authUser = () => {
	return axios({
		method: 'post',
		url: '/api/login',
		responseType: 'json'
	});
};
authUser().then(response => {
	ReactDOM.render(
		<Provider store={store}>
			<Router>{response.status === 200 ? <AppWithRouter /> : <Login />}</Router>
		</Provider>,
		document.getElementById('root')
	);
});

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
