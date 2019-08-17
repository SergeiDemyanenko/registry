const initialDirectoryState = {
    peopleLoading: false,
    people: [],
    columns: [],
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
}
