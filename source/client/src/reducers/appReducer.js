const initialAppState = {
	leftMenuOpen: false,
	leftMenuItems: []
};

export default (state = initialAppState, action) => {
	switch (action.type) {
		case 'TOGGLE_LEFT_MENU':
			state = {
				...state,
				leftMenuOpen: !state.leftMenuOpen
			};
			break;
		case 'TOGGLE_LEFT_MENU_ITEM':
			state = {
				...state,
				leftMenuItems: toggleLeftMenuItemByName(action.payload, state.leftMenuItems)
			};
			break;
		case 'SET_MENU':
			state = {
				...state,
				leftMenuItems: action.payload
			};
			break;
		default:
			return state;
	}
	return state;
};

/**
 * Toggles left menu item by its name
 * @param {*} name
 * @param {*} state
 * @returns {*} leftMenuItems with toggled isExpanded propertie for matched item by name
 */
function toggleLeftMenuItemByName(name, leftMenuItems) {
	return leftMenuItems.map(leftMenuItem => {
		if (leftMenuItem.name === name) {
			return {
				...leftMenuItem,
				isExpanded: !leftMenuItem.isExpanded
			};
		} else if (leftMenuItem.items) {
			return {
				...leftMenuItem,
				items: toggleLeftMenuItemByName(name, leftMenuItem.items)
			};
		}
		return {
			...leftMenuItem,
			isExpanded: false
		};
	});
}
