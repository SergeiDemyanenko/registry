export default (theme) => ({
    tableContainer: {
        height: 'calc(100vh - 140px)',
        overflow: 'scroll',
    },
    loadingIndicatorContainer: {
        height: 'inherit',
        display: 'flex',
        justifyContent: 'center',
        flexDirection: 'column',
        alignItems: 'center'
    },
    button:  {
        margin: theme.spacing(1),
      },
      input: {
        display: 'none',
      },
})