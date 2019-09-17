import React from 'react';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Input from '@material-ui/core/Input';
import Button from '@material-ui/core/Button';
import { withStyles } from '@material-ui/core/styles';
import formDialogStyles from '../../styles/FormDialog.styles';

class FormDialog extends React.Component {
	render() {
		return (
			<Dialog open={this.props.open} aria-labelledby='alert-dialog-title' aria-describedby='alert-dialog-description'>
				<DialogTitle id='alert-dialog-title'>Добавить физическое лицо</DialogTitle>
				<DialogContent>
					<Input
						className={this.props.classes.formDialogInput}
						inputProps={{
							'aria-label': 'description'
						}}
					/>
					<Input
						className={this.props.classes.formDialogInput}
						inputProps={{
							'aria-label': 'description'
						}}
					/>
					<DialogContentText id='alert-dialog-description' />
				</DialogContent>
				<DialogActions>
					<Button variant='contained' color='secondary' className={this.props.classes.button} onClick={() => this.props.onDismiss()}>
						Отменить
					</Button>
					<Button variant='contained' color='primary' className={this.props.classes.button}>
						Подтвердить
					</Button>
				</DialogActions>
			</Dialog>
		);
	}
}
export default withStyles(formDialogStyles)(FormDialog);
