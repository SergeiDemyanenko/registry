import React from 'react';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Button from '@material-ui/core/Button';
import { withStyles } from '@material-ui/core/styles';
import formDialogStyles from '../../styles/FormDialog.styles';
import { TextField } from '@material-ui/core';

class FormDialog extends React.Component {
	render() {
		return (
			<Dialog open={this.props.open}>
				<DialogTitle id='alert-dialog-title'>Добавить физическое лицо</DialogTitle>
				<DialogContent>
					{this.props.columns
						.filter(column => column.visible !== false)
						.map((column, columnIndex) => (
							<form key={columnIndex} className={this.props.classes.formDialogInput}>
								<TextField className={this.props.classes.TextField} label={column.name} margin='normal' />
							</form>
						))}
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
