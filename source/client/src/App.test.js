import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { BrowserRouter as Router, withRouter} from 'react-router-dom';
import {Provider} from 'react-redux';
import store from './store';

it('renders without crashing', () => {
  const div = document.createElement('div');
  const AppWithRouter = withRouter(props => <App {...props}/>);
  ReactDOM.render(<Provider store={store}>
    <Router>
         <AppWithRouter />
    </Router>
</Provider>, div);
  ReactDOM.unmountComponentAtNode(div);
});
