import React from 'react';
import { Route } from 'react-router-dom';
import { 
    AppStatus, 
    Characteristics,
    CheckResult,
    Department,
    Expulsion,
    FindSumContact,
    FindSumHarm,
    Infrigements,
    Insurances,
    InsuranceSubj,
    LegalForms,
    LevelContract,
    LevelHarm,
    Limit,
    Measures,
    OrganIssued,
    Perilous,
    Protocols,
    Regions,
    ScopeWork,
    StatusCheck,
    TypeOfWork
} from '../components/directory';
import Person from '../components/directory/Person'


export const DirectoryRoutes = ({ match }) => {
  return (
    <div>
      <Route path={`${match.url}/person`} component={Person}/>
      <Route path={`${match.url}/legalforms`} component={LegalForms}/>
      <Route path={`${match.url}/organissued`} component={OrganIssued}/>
      <Route path={`${match.url}/applicationstatus`} component={AppStatus}/>
      <Route path={`${match.url}/characteristics`} component={Characteristics}/>
      <Route path={`${match.url}/infrigements`} component={Infrigements}/>
      <Route path={`${match.url}/levelharm`} component={LevelHarm}/>
      <Route path={`${match.url}/levelcontract`} component={LevelContract}/>
      <Route path={`${match.url}/measures`} component={Measures}/>
      <Route path={`${match.url}/expulsion`} component={Expulsion}/>
      <Route path={`${match.url}/perilous`} component={Perilous}/>
      <Route path={`${match.url}/limit`} component={Limit}/>
      <Route path={`${match.url}/fundsumharm`} component={FindSumHarm}/>
      <Route path={`${match.url}/fundsumcontact`} component={FindSumContact}/>
      <Route path={`${match.url}/insurancesubject`} component={InsuranceSubj}/>
      <Route path={`${match.url}/insurances`} component={Insurances}/>
      <Route path={`${match.url}/statuscheck`} component={StatusCheck}/>
      <Route path={`${match.url}/department`} component={Department}/>
      <Route path={`${match.url}/scopework`} component={ScopeWork}/>
      <Route path={`${match.url}/checkresult`} component={CheckResult}/>
      <Route path={`${match.url}/regions`} component={Regions}/>
      <Route path={`${match.url}/protocols`} component={Protocols}/>
      <Route path={`${match.url}/typeofwork`} component={TypeOfWork}/>
    </div>
  )
};
