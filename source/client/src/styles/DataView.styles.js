export default theme => ({
	tableContainer: {
		height: 'calc(100vh - 140px)',
		overflow: 'scroll'
	},
	loadingIndicatorContainer: {
		height: 'inherit',
		display: 'flex',
		justifyContent: 'center',
		flexDirection: 'column',
		alignItems: 'center'
	},
	button: {
		margin: theme.spacing(1),
		fontSize: 40
	},
	input: {
		display: 'none'
	},
	paper: {
		position: 'absolute',
		width: 400,
		backgroundColor: theme.palette.background.paper,
		border: '2px solid #000',
		boxShadow: theme.shadows[5],
		padding: theme.spacing(2, 4, 3)
	}
});
