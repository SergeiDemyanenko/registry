import React from 'react';
import axios from 'axios';
import { connect } from 'react-redux';
import CircularProgress from '@material-ui/core/CircularProgress';
import dataViewStyles from '../styles/DataView.styles';
import { withStyles } from '@material-ui/core/styles';
import DynamicTable from './DynamicTable';
import FormDialog from './shared/FormDialog';
import DeleteIcon from '@material-ui/icons/Delete';
import AddBoxIcon from '@material-ui/icons/AddBox';
import IconButton from '@material-ui/core/IconButton';

class DataView extends React.Component {
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
			<div className={this.props.classes.tableContainer}>
				{this.props.data.loading ? (
					loadingIndicator
				) : (
					<div>
						<FormDialog open={this.state.open} columns={this.props.data.columns} onDismiss={() => this.toggleDialog()} />
						<IconButton color='primary' className={this.props.classes.button} onClick={() => this.toggleDialog()}>
							<AddBoxIcon />
						</IconButton>
						<IconButton color='secondary' className={this.props.classes.button} aria-label='delete'>
							<DeleteIcon />
						</IconButton>
						<DynamicTable columns={this.props.data.columns} rows={this.props.data.data} />
					</div>
				)}
			</div>
		);
	}

	toggleDialog() {
		this.setState(state => ({
			open: !state.open
		}));
	}

	componentDidMount() {
		this.props.setLoading(true);
		this.fetchData().then(response => {
			this.props.setLoading(false);
			this.props.setColumns(response.data.FORM);
			this.props.setData(response.data.DATA);
		});
	}

	fetchData() {
		return axios({
			method: 'get',
			url: `api/model/${this.props.location.pathname}`,
			responseType: 'json'
		});
	}
}

const mapStateToProps = state => {
	return {
		data: state.dataReducer
	};
};

const mapDispatchToProps = dispatch => {
	return {
		setData: data => {
			dispatch({
				type: 'SET_DATA',
				payload: data
			});
		},
		setLoading: isLoading => {
			dispatch({
				type: 'SET_LOADING',
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
)(withStyles(dataViewStyles)(DataView));
