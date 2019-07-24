const initialAppState = {
    leftMenuOpen: false
}

export default (state = initialAppState, action) => {
    switch(action.type){
        case 'TOGGLE_LEFT_MENU':
           state = { 
               ...state,
               leftMenuOpen: !state.leftMenuOpen
           };
           break;
        default: 
        return state;
    }
    return state;
}
