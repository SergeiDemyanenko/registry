import { initialAppState } from '../store/appState';

export default (state = initialAppState, action) => {
    switch(action.type){
        case 'TOGGLE_LEFT_MENU':
           state = { 
               ...state,
               leftMenuOpen: !state.leftMenuOpen
           };
           break;
        case 'TOGGLE_LEFT_MENU_ITEM':
            state = {
                ...state,
                leftMenuItems: state.leftMenuItems.map((item, index) => {
                    if(index !== action.payload) {
                        return {
                            ...item,
                            isExpanded: false
                        }
                    }
                    return {
                        ...item,
                        isExpanded: !item.isExpanded
                    }
                })
            }
            break;
        default: 
        return state;
    }
    return state;
}
