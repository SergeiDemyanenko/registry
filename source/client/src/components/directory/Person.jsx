import React from 'react';
import axios from 'axios';
import { connect } from 'react-redux';
import CircularProgress from '@material-ui/core/CircularProgress';
import personStyles from '../../styles/Person.styles';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import DynamicTable from '../DynamicTable';
import FormDialog from '../shared/FormDialog';

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
				<Button variant='contained' color='primary' className={this.props.classes.button} onClick={() => this.toggleDialog()}>
					Добавить
				</Button>

				<Button variant='contained' color='secondary' className={this.props.classes.button}>
					Удалить
				</Button>
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

	componentWillMount() {
		this.props.setPeopleLoading(true);
		this.fetchPeople().then(response => {
			this.props.setPeopleLoading(false);
			this.props.setColumns(response.data.HEAD);
			this.props.setPeople(response.data.DATA);
		});
	}

	fetchPeople() {
		return axios({
			method: 'get',
			url: '/api/directory/person',
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
