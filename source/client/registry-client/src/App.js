import React from 'react';
import './App.css';
import {connect} from 'react-redux';
import AppBar from '@material-ui/core/AppBar';
import Divider from '@material-ui/core/Divider';
import Drawer from '@material-ui/core/Drawer';
import Hidden from '@material-ui/core/Hidden';
import IconButton from '@material-ui/core/IconButton';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MenuIcon from '@material-ui/icons/Menu';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import appStyles from './App.styles';
// import leftMenuItems from './MenuItems';
import { Route, Link } from 'react-router-dom';
import { Documents } from './components/Documents';
import { Reports } from './components/Reports';
import { Directory }  from './components/Directory';
import { Settings } from './components/Settings';
import { Help } from './components/Help';
import Collapse from '@material-ui/core/Collapse';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';

class App extends React.Component {
  render(){

    const { classes } = this.props;

    const drawer = (
      <div>
        <div className={classes.toolbar} />
        <Divider />
        <List>
            {this.props.app.leftMenuItems.map((leftMenuItem, i) => 
            <div key={leftMenuItem.title}>
                <ListItem button  key={leftMenuItem.title}
                  onClick={() => { this.props.toggleLeftMenuItem(i) }}
                  selected={this.props.location.pathname === leftMenuItem.link}>    
                  <ListItemIcon>{leftMenuItem.icon}</ListItemIcon>
                  <ListItemText primary={leftMenuItem.title} />
                  {leftMenuItem.isExpanded ? <ExpandLess /> : <ExpandMore />}
                </ListItem>
                <Collapse in={leftMenuItem.isExpanded} timeout="auto" unmountOnExit>
                  <List>
                    {leftMenuItem.nestedItems.map(nestedItem => (
                      <Link to={nestedItem.link} className={classes.link} key={nestedItem.title}>
                        <ListItem button className={classes.nested}>
                          <ListItemText primary={nestedItem.title}/>
                        </ListItem>
                      </Link>
                    ))}
                  </List>
                </Collapse>
            </div>
          )}
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
            <Route path="/directory/" component={Directory}/>
            <Route path="/documents/" component={Documents}/>
            <Route path="/reports/" component={Reports}/>
            <Route path="/settings/" component={Settings}/>
            <Route path="/help/" component={Help}/>
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
    },
    toggleLeftMenuItem: (index) => {
      dispatch({
        type: 'TOGGLE_LEFT_MENU_ITEM',
        payload: index
      })
    }
  }
}

export default connect(mapStateToProps,mapDispatchToProps)(withStyles(appStyles)(App));