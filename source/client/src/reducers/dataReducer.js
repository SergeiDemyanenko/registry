const initialDataState = {
	loading: false,
	data: [],
	columns: []
};

export default (state = initialDataState, action) => {
	switch (action.type) {
		case 'SET_DATA':
			state = {
				...state,
				// temporary workaround for rendering performance
				data: action.payload.slice(0, 10)
			};
			break;
		case 'SET_LOADING':
			state = {
				...state,
				loading: action.payload
			};
			break;
		case 'SET_COLUMNS':
			state = {
				...state,
				columns: action.payload
			};
			break;
		default:
			return state;
	}
	return state;
};
