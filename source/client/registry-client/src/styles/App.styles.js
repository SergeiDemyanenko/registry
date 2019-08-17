const drawerWidth = 240;

export default theme => ({
  root: {
    display: 'flex',
  },
  drawer: {
    [theme.breakpoints.up('sm')]: {
      width: drawerWidth,
      flexShrink: 0,
    },
  },
  appBar: {
    marginLeft: drawerWidth,
    [theme.breakpoints.up('sm')]: {
      width: `calc(100% - ${drawerWidth}px)`,
    },
  },
  menuButton: {
    color: "inherit",
    ariaLabel: "Open drawer",
    edge: "start",
    marginRight:theme.spacing(2),
    [theme.breakpoints.up('sm')]: {
      display: 'none',
    },
  },
  toolbar: theme.mixins.toolbar,
  drawerPaper: {
    width: drawerWidth,
  },
  content: {
    flexGrow: 1,
    paddingLeft: theme.spacing(3),
    paddingRight: theme.spacing(3),
    width: 'calc(100vw - 288px)',
  },
  nested: {
    paddingLeft: theme.spacing(10),
  },

  link:{
    color: 'inherit',
    textDecoration: 'none',
  }
});