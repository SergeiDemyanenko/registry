import React from 'react';
import SettingsIcon from '@material-ui/icons/Settings';
import LibraryIcon from '@material-ui/icons/LibraryBooks';
import StorageIcon from '@material-ui/icons/Storage'
import ReportIcon from '@material-ui/icons/Report';
import HelpIcon from '@material-ui/icons/Help';
import { LeftMenuItem } from '../models/LeftMenuItem';

export const initialAppState = {
    leftMenuOpen: false,
    leftMenuItems: [
        {
          title: 'Справочники',
          icon: <LibraryIcon color="primary"/> ,
          link: '/directory',
          isExpanded: false,
          nestedItems: [
              new LeftMenuItem('Физические лица', <LibraryIcon color="primary"/>, '/directory/person'),
              new LeftMenuItem('Правовые формы', <LibraryIcon color="primary"/>,'/directory/legalforms'),
              new LeftMenuItem('Регистрирующий орган', <LibraryIcon color="primary"/>,'/directory/organissued'),
              new LeftMenuItem('Статус заявки', <LibraryIcon color="primary"/>,'/directory/applicationstatus'),
              new LeftMenuItem('Характеристики',  <LibraryIcon color="primary"/>,'/directory/characteristics'),
              new LeftMenuItem('Нарушения', <LibraryIcon color="primary"/>,'/directory/infrigements'),
              new LeftMenuItem('Уровень ВВ', <LibraryIcon color="primary"/>,'/directory/levelharm'),
              new LeftMenuItem('Уровень ОДО', <LibraryIcon color="primary"/>,'/directory/levelcontract'),
              new LeftMenuItem('Меры воздействия', <LibraryIcon color="primary"/>,'/directory/measures'),
              new LeftMenuItem('Основания для исключения', <LibraryIcon color="primary"/>,'/directory/expulsion'),
              new LeftMenuItem('Тип опасности работ', <LibraryIcon color="primary"/>,'/directory/perilous'),
              new LeftMenuItem('Лимит', <LibraryIcon color="primary"/>,'/directory/limit'),
              new LeftMenuItem('Компенсационный фонд ВВ', <LibraryIcon color="primary"/>,'/directory/fundsumharm'),
              new LeftMenuItem('Компенсанционный фонд ОДО', <LibraryIcon color="primary"/>,'/directory/fundsumcontact'),
              new LeftMenuItem('Предмет договора страхования', <LibraryIcon color="primary"/>,'/directory/insurancesubject'),
              new LeftMenuItem('Страховые', <LibraryIcon color="primary"/>,'/directory/insurances'),
              new LeftMenuItem('Вид проверки', <LibraryIcon color="primary"/>,'/directory/statuscheck'),
              new LeftMenuItem('Орган СРО', <LibraryIcon color="primary"/>,'/directory/department'),
              new LeftMenuItem('Обьем работ', <LibraryIcon color="primary"/>,'/directory/scopework'),
              new LeftMenuItem('Результат проверки', <LibraryIcon color="primary"/>,'/directory/checkresult'),
              new LeftMenuItem('Регионы', <LibraryIcon color="primary"/>,'/directory/regions'),
              new LeftMenuItem('Протоколы', <LibraryIcon color="primary"/>,'/directory/protocols'),
              new LeftMenuItem('Вид работ', <LibraryIcon color="primary"/>,'/directory/typeofwork'),

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