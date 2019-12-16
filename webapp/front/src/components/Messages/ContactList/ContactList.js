import React, { Component } from 'react'
import styles from './ContactList.module.css'
import SearchBox from './SearchBox/SearchBox'
import ContactItem from './ContactItem/ContactItem'

class ContactList extends Component {

    state = {
        filterSearch: '',
    }

    handleFilterSearch = (event) => {
        this.setState({filterSearch: event.target.value})
    }

    render () {
        return (
            <div className={styles.ContactList}>
                <SearchBox handleFilterSearch={this.handleFilterSearch} value={this.state.filterSearch} />
                <div className={styles.Contacts}>
                    {this.props.contacts.filter(contact => {
                        return contact.name.toLowerCase().includes(this.state.filterSearch.toLowerCase())
                    }).map(contact => {
                        return (<ContactItem 
                            key={contact.id}
                            onClick={() => this.props.handleContactClick(contact.id)}
                            name={contact.name}
                            previewMsg={contact.msg}
                            img={contact.img}
                            active={this.props.activeChat === contact.id} />)
                    })}
                </div>
            </div>
        )
    }
}

export default ContactList