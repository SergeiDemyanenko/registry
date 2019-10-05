import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import withMobileDialog from '@material-ui/core/withMobileDialog';
import { withStyles } from '@material-ui/core';
import loginStyles from '../styles/Login.styles';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';
import IconButton from '@material-ui/core/IconButton';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import InputAdornment from '@material-ui/core/InputAdornment';
import FormControl from '@material-ui/core/FormControl';

class Login extends React.Component {
	constructor(props) {
		super(props);
		this.state = { showPassword: false };
	}
	render() {
		return (
			<Dialog open onRequestClose={this.props.toggleLogin} fullScreen={this.props.fullScreen}>
				<DialogTitle className={this.props.classes.dialogTitle}>Login</DialogTitle>
				<DialogContent>
					<DialogContentText>Please enter your Username and Password</DialogContentText>
					<TextField autoFocus margin='dense' id='name' label='Username' type='email' fullWidth />
					<FormControl autoFocus margin='dense' id='name' label='Username' type='email' fullWidth>
						<InputLabel htmlFor='adornment-password'>Password</InputLabel>
						<Input
							id='adornment-password'
							type={this.state.showPassword ? 'text' : 'password'}
							value={this.state.password}
							onChange={this.handleChange('password')}
							endAdornment={
								<InputAdornment position='end'>
									<IconButton aria-label='Toggle password visibility' onClick={this.handleClickShowPassword}>
										{this.state.showPassword ? <Visibility /> : <VisibilityOff />}
									</IconButton>
								</InputAdornment>
							}
						/>
					</FormControl>
				</DialogContent>
				<DialogActions>
					<Button onClick={this.props.toggleLogin} color='primary' className={this.props.classes.loginButton}>
						Login
					</Button>
				</DialogActions>
			</Dialog>
		);
	}

	handleClickShowPassword = () => {
		this.setState(state => ({ showPassword: !state.showPassword }));
	};

	handleChange = prop => event => {
		this.setState({ [prop]: event.target.value });
	};
}

export default withMobileDialog()(withStyles(loginStyles)(Login));
