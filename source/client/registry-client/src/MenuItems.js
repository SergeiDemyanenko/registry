import React from 'react';
import SettingsIcon from '@material-ui/icons/Settings';
import LibraryIcon from '@material-ui/icons/LibraryBooks';
import StorageIcon from '@material-ui/icons/Storage'
import ReportIcon from '@material-ui/icons/Report';
import HelpIcon from '@material-ui/icons/Help'

const leftMenuItems = [
    {
        title: 'Справочники',
        icon: <LibraryIcon color="primary"/> ,
        link: '/directory'
      },
      {
        title: 'Документы',
        icon: <StorageIcon color="primary"/>, 
        link: '/documents'
      },
      {
        title: 'Отчеты',
        icon: <ReportIcon color="primary" />,
        link: '/reports'
      },
      {
        title: 'Настройки',
        icon: <SettingsIcon color="primary" />,
        link: '/settings'
      },
      {
        title: 'Помощь',
        icon: <HelpIcon color="primary" />,
        link: '/help'
      },
    ];
    export default leftMenuItems;