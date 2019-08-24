import React from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

export class DynamicTable extends React.Component{
    render(){
        return (<Table>
            <TableHead>
            <TableRow>
              {/* TODO: Hide ID_PERSON column */}
                {this.props.columns.map(column => (
                  <TableCell>{column}</TableCell>
                ))}
            </TableRow>
            </TableHead>
            <TableBody>
              {/* TODO: Hide ID_PERSON column */}
                {this.props.rows.map((row, rowIndex) => (
                    <TableRow key={rowIndex}>
                      {this.props.columns.map((column, columnIndex) => (
                        <TableCell>{row[columnIndex]}</TableCell>
                      ))}
                    </TableRow>
                ))}
            </TableBody>
        </Table>)
    }
}