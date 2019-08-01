const initialDirectoryState = {
    peopleLoading: false,
    people: []
}

export default (state = initialDirectoryState, action) => {
    switch(action.type){
        case 'SET_PEOPLE':
           state = { 
               ...state,
               people: action.payload
           };
           break;
        case 'SET_PEOPLE_LOADING':
            state = {
                ...state,
                peopleLoading: action.payload
            };
            break;
        // add case for SET_PEOPLE_LOADING
        default: 
        return state;
    }
    return state;
}
