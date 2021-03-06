import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import 'whatwg-fetch';
import update from 'react-addons-update';

const API_HEADERS = {
	'Content-Type': 'application/json',
	Authorization: 'any-string-you-like'
}

class AccountContainer extends Component {
  constructor() {
	super();
	this.state={
	    accounts:[]
    }
  }

  componentDidMount(){
    fetch('./rest/accounts')
    .then((response) => response.json())
    .then((responseData) => {
    	this.setState({accounts: responseData});
    })
    .catch((error) => {
    	console.log('Error fetching and parsing data', error);
    });
  }
  
  addAccount(name){
	// Keep reference to original state prior to mutations, so can revert 
	// optimistic changes
	let prevState = this.state
	    
	let newAccount = {name: name};
	let nextState = update(this.state.accounts, {$push: [newAccount]});
	    
	this.setState({accounts:nextState});
	
	fetch('rest/accounts', {
        method: 'POST',
        headers: API_HEADERS,
        body: JSON.stringify(newAccount)
	})
	.then((response) => {
	  if(response.ok){
		return response.json();
	  } else {
		throw new Error("Server response wasn't OK");
	  }
	})
	.then((responseData) => {
	  newAccount.id=responseData.id;
	  this.setState({accounts:nextState});
	})
	.catch((error) => {
    	console.log('Error posting account data', error);
    	this.setState(prevState);
    });
  } 
  
  render() { 
    return (
      <div>
        { React.Children.map( this.props.children, child => React.cloneElement(child, {
        	  accounts: this.state.accounts, 
        	  accountCallbacks: {add: this.addAccount.bind(this)}
            }))
        }
      </div>
    );
  }
}

export default AccountContainer;