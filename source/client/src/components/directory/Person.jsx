import React from 'react';
import axios from 'axios';
import { connect } from 'react-redux';
import CircularProgress from '@material-ui/core/CircularProgress';
import personStyles from '../../styles/Person.styles';
import { withStyles } from '@material-ui/core/styles';
import DynamicTable from '../DynamicTable';
import FormDialog from '../shared/FormDialog';
import DeleteIcon from '@material-ui/icons/Delete';
import AddBoxIcon from '@material-ui/icons/AddBox';
import IconButton from '@material-ui/core/IconButton';

class Person extends React.Component {
	constructor(props) {
		super(props);
		this.state = { open: false };
	}
	render() {
		const loadingIndicator = (
			<div className={this.props.classes.loadingIndicatorContainer}>
				<CircularProgress />
			</div>
		);
		return (
			<div>
				<FormDialog open={this.state.open} columns={this.props.directory.columns} onDismiss={() => this.toggleDialog()} />
				<IconButton color='primary' className={this.props.classes.button} onClick={() => this.toggleDialog()}>
					<AddBoxIcon />
				</IconButton>
				<IconButton color='secondary' className={this.props.classes.button} aria-label='delete'>
					<DeleteIcon />
				</IconButton>
				<div className={this.props.classes.tableContainer}>
					{this.props.directory.peopleLoading ? (
						loadingIndicator
					) : (
						<DynamicTable columns={this.props.directory.columns} rows={this.props.directory.people} />
					)}
				</div>
			</div>
		);
	}

	toggleDialog() {
		this.setState(state => ({
			open: !state.open
		}));
	}

	componentDidMount() {
		this.props.setPeopleLoading(true);
		this.fetchPeople().then(response => {
			this.props.setPeopleLoading(false);
			this.props.setColumns(response.data.FORM);
			this.props.setPeople(response.data.DATA);
		});
	}

	fetchPeople() {
		return axios({
			method: 'get',
			url: '/api/model/person',
			responseType: 'json'
		});
	}
}
const mapStateToProps = state => {
	return {
		directory: state.directoryReducer
	};
};
const mapDispatchToProps = dispatch => {
	return {
		setPeople: people => {
			dispatch({
				type: 'SET_PEOPLE',
				payload: people
			});
		},
		setPeopleLoading: isLoading => {
			dispatch({
				type: 'SET_PEOPLE_LOADING',
				payload: isLoading
			});
		},
		setColumns: columns => {
			dispatch({
				type: 'SET_COLUMNS',
				payload: columns
			});
		}
	};
};

export default connect(
	mapStateToProps,
	mapDispatchToProps
)(withStyles(personStyles)(Person));
