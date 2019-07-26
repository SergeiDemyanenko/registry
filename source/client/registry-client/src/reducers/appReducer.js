import React from 'react';
import SettingsIcon from '@material-ui/icons/Settings';
import LibraryIcon from '@material-ui/icons/LibraryBooks';
import StorageIcon from '@material-ui/icons/Storage'
import ReportIcon from '@material-ui/icons/Report';
import HelpIcon from '@material-ui/icons/Help'
import { LeftMenuItem } from '../models/LeftMenuItem';

const initialAppState = {
    leftMenuOpen: false,
    leftMenuItems: [
        {
          title: 'Справочники',
          icon: <LibraryIcon color="primary"/> ,
          link: '/directory',
          isExpanded: false,
          nestedItems: [
              new LeftMenuItem('nested1', <LibraryIcon color="primary"/>,'/directory/nested1')
          ]
        },
        {
          title: 'Документы',
          icon: <StorageIcon color="primary"/>, 
          link: '/documents',
          isExpanded: false,
          nestedItems: [
              new LeftMenuItem('nested2', <LibraryIcon color="primary"/>,'/documents/nested2')
          ]
        },
        {
          title: 'Отчеты',
          icon: <ReportIcon color="primary" />,
          link: '/reports',
          isExpanded: false,
          nestedItems: [
              new LeftMenuItem('nested3', <LibraryIcon color="primary"/>,'/reports/nested3')
          ]
        },
        {
          title: 'Настройки',
          icon: <SettingsIcon color="primary" />,
          link: '/settings',
          isExpanded: false,
          nestedItems: [
              new LeftMenuItem('nested4', <LibraryIcon color="primary"/>,'/settings/nested4')
          ]
        },
        {
          title: 'Помощь',
          icon: <HelpIcon color="primary" />,
          link: '/help',
          isExpanded: false,
          nestedItems: [
              new LeftMenuItem('nested5', <LibraryIcon color="primary"/>,'/help/nested5')
          ]
        },
      ]
}

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
