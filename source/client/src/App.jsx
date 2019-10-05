import React from 'react';
import './App.css';
import { connect } from 'react-redux';
import AppBar from '@material-ui/core/AppBar';
import Divider from '@material-ui/core/Divider';
import Drawer from '@material-ui/core/Drawer';
import Hidden from '@material-ui/core/Hidden';
import IconButton from '@material-ui/core/IconButton';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import MenuIcon from '@material-ui/icons/Menu';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import appStyles from './styles/App.styles';
import { Route, Link } from 'react-router-dom';
import Collapse from '@material-ui/core/Collapse';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
import axios from 'axios';
import Person from './components/directory/Person';
import Login from './components/Login';

class App extends React.Component {
	componentDidMount() {
		this.fetchMenu().then(response => {
			this.props.setMenu(response.data);
		});
	}

	fetchMenu() {
		return axios({
			method: 'get',
			url: '/api/menu',
			responseType: 'json'
		});
	}

	/**
	 * Recursivly builds deep nested Menu
	 * @param {*} leftMenuItems
	 */
	buildMenu(leftMenuItems, path = '') {
		return (
			<List>
				{leftMenuItems.map(leftMenuItem => {
					if (leftMenuItem.items) {
						return (
							<div key={leftMenuItem.title}>
								<ListItem
									button
									key={leftMenuItem.title}
									onClick={() => {
										this.props.toggleLeftMenuItem(leftMenuItem.name);
									}}
									selected={this.props.location.pathname === leftMenuItem.link}>
									<ListItemText primary={leftMenuItem.title} />
									{leftMenuItem.isExpanded ? <ExpandLess /> : <ExpandMore />}
								</ListItem>
								<Collapse in={leftMenuItem.isExpanded} timeout='auto' unmountOnExit className={this.props.classes.nested}>
									{this.buildMenu(leftMenuItem.items, leftMenuItem.name)}
								</Collapse>
							</div>
						);
					} else {
						/**
						 * temporary solution for url paths
						 */
						path = leftMenuItem.name;
						return (
							<Link to={path} className={this.props.classes.link} key={leftMenuItem.title}>
								<ListItem button key={leftMenuItem.title}>
									<ListItemText primary={leftMenuItem.title} />
								</ListItem>
							</Link>
						);
					}
				})}
			</List>
		);
	}

	getDrawer() {
		return (
			<div>
				<div className={this.props.classes.toolbar} />
				<Divider />
				{this.buildMenu(this.props.app.leftMenuItems)}
			</div>
		);
	}

	render() {
		const { classes } = this.props;
		return (
			<div className={classes.root}>
				<AppBar position='fixed' className={classes.appBar}>
					<Toolbar>
						<IconButton onClick={this.props.toggleLeftMenu} className={classes.menuButton}>
							<MenuIcon />
						</IconButton>
						<Typography variant='h6' noWrap>
							Registry Client
						</Typography>
					</Toolbar>
				</AppBar>
				<nav className={classes.drawer} aria-label='Mailbox folders'>
					{/* The implementation can be swapped with js to avoid SEO duplication of links. */}
					<Hidden smUp implementation='css'>
						<Drawer
							variant='temporary'
							// anchor={theme.direction === 'rtl' ? 'right' : 'left'}
							open={this.props.app.leftMenuOpen}
							onClick={this.props.toggleLeftMenu}
							classes={{
								paper: classes.drawerPaper
							}}
							ModalProps={{
								keepMounted: true // Better open performance on mobile.
							}}>
							{this.getDrawer()}
						</Drawer>
					</Hidden>
					<Hidden xsDown implementation='css'>
						<Drawer
							classes={{
								paper: classes.drawerPaper
							}}
							variant='permanent'
							open>
							{this.getDrawer()}
						</Drawer>
					</Hidden>
				</nav>
				<main className={classes.content}>
					<div className={classes.toolbar} />
					<Route path='/person' component={Person} />
					<Route path='/login' component={Login} />
				</main>
			</div>
		);
	}
}

const mapStateToProps = state => {
	return {
		app: state.appReducer
	};
};
const mapDispatchToProps = dispatch => {
	return {
		setName: name => {
			dispatch({
				type: 'SET_NAME',
				payload: name
			});
		},
		toggleLeftMenu: () => {
			dispatch({
				type: 'TOGGLE_LEFT_MENU'
			});
		},
		toggleLeftMenuItem: name => {
			dispatch({
				type: 'TOGGLE_LEFT_MENU_ITEM',
				payload: name
			});
		},
		setMenu: menu => {
			dispatch({
				type: 'SET_MENU',
				payload: menu
			});
		}
	};
};
export default connect(
	mapStateToProps,
	mapDispatchToProps
)(withStyles(appStyles)(App));
