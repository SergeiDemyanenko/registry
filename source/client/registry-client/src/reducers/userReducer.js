const initialUserState = {
    firstName: '',
}

export default (state = initialUserState, action) => {
    switch(action.type){
        case 'SET_NAME':
           state = { 
               ...state,
               firstName: action.payload
           };
           break;
        default: 
        return state;
    }
    return state;
}
