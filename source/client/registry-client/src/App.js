import React from 'react';
import './App.css';
import {Home} from './components/Home';
import {Header} from './components/Header';
import {connect} from 'react-redux';
import Button from '@material-ui/core/Button';
import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import CssBaseline from '@material-ui/core/CssBaseline';
import Divider from '@material-ui/core/Divider';
import Drawer from '@material-ui/core/Drawer';
import Hidden from '@material-ui/core/Hidden';
import IconButton from '@material-ui/core/IconButton';
import InboxIcon from '@material-ui/icons/MoveToInbox';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MailIcon from '@material-ui/icons/Mail';
import MenuIcon from '@material-ui/icons/Menu';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import appStyles from './App.styles';

class App extends React.Component {
  render(){
    const { classes } = this.props;
    const drawer = (
      <div>
        <div className={classes.toolbar} />
        <Divider />
        <List>
          {['Справочники', 'Документы', 'Отчеты', 'Настройки', 'Помощь'].map((text, index) => (
            <ListItem button key={text}>
              <ListItemIcon>{index % 2 === 0 ? <InboxIcon /> : <MailIcon />}</ListItemIcon>
              <ListItemText primary={text} />
            </ListItem>
          ))}
        </List>
      </div>
    );

    return (
      <div className={classes.root}>
        <AppBar position="fixed" className={classes.appBar}>
          <Toolbar>
            <IconButton
              color="inherit"
              aria-label="Open drawer"
              edge="start"
              onClick={this.props.toggleLeftMenu}
              className={classes.menuButton}
            >
              <MenuIcon />
            </IconButton>
            <Typography variant="h6" noWrap>
              Registry Client
            </Typography>
          </Toolbar>
        </AppBar>
        <nav className={classes.drawer} aria-label="Mailbox folders">
          {/* The implementation can be swapped with js to avoid SEO duplication of links. */}
          <Hidden smUp implementation="css">
            <Drawer
              variant="temporary"
              // anchor={theme.direction === 'rtl' ? 'right' : 'left'}
              open={this.props.app.leftMenuOpen}
              onClick={this.props.toggleLeftMenu}
              classes={{
                paper: classes.drawerPaper,
              }}
              ModalProps={{
                keepMounted: true, // Better open performance on mobile.
              }}
            >
              {drawer}
            </Drawer>
          </Hidden>
          <Hidden xsDown implementation="css">
            <Drawer
              classes={{
                paper: classes.drawerPaper,
              }}
              variant="permanent"
              open
            >
              {drawer}
            </Drawer>
          </Hidden>
        </nav>
        <main className={classes.content}>
          <div className={classes.toolbar} />
                BODY
        </main>
      </div>
    );
  } 
}

const mapStateToProps = (state) => {
  return {
    user: state.userReducer,
    app: state.appReducer
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    setName: (name) => {
      dispatch({
        type: 'SET_NAME',
        payload: name,
      })
    },
    toggleLeftMenu: () => {
      dispatch({
        type: 'TOGGLE_LEFT_MENU',
      })
    }
  }
}

export default connect(mapStateToProps,mapDispatchToProps)(withStyles(appStyles)(App));