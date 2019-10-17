import React from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import { withStyles } from '@material-ui/core/styles';
import dynamicTableStyles from '../styles/DynamicTable.styles';

class DynamicTable extends React.Component {
	constructor(props) {
		super(props);
		this.state = { hoveredIndex: null };
	}
	render() {
		return (
			<Table>
				<TableHead>
					<TableRow>
						{this.props.columns
							.filter(column => column.visible !== false)
							.map((column, columnIndex) => (
								<TableCell key={columnIndex}>{column.title}</TableCell>
							))}
					</TableRow>
				</TableHead>
				<TableBody>
					{this.props.rows.map((row, rowIndex) => (
						<TableRow
							key={rowIndex}
							onMouseEnter={() => this.onRowMouseEnter(rowIndex)}
							className={this.state.hoveredIndex === rowIndex ? this.props.classes.rowHover : ''}>
							{this.props.columns
								.filter(column => column.visible !== false)
								.map((column, columnIndex) => (
									<TableCell key={columnIndex}>{row[columnIndex]}</TableCell>
								))}
						</TableRow>
					))}
				</TableBody>
			</Table>
		);
	}

	onRowMouseEnter(index) {
		this.setState(state => ({
			hoveredIndex: index
		}));
	}
}
export default withStyles(dynamicTableStyles)(DynamicTable);
