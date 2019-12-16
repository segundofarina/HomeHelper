import React from 'react'
import styles from './SearchBox.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearch } from '@fortawesome/free-solid-svg-icons'

const searchBox = (props) => {
    const searchInput = React.createRef()

    const focusSearchInput = () => {
        searchInput.current.focus()
    }

    return (
        <div className={styles.SearchBox}>
            <div className={styles.Icon} onClick={focusSearchInput}>
                <FontAwesomeIcon icon={faSearch} />
            </div>
            <input type="text"
                className={styles.Input}
                onChange={props.handleFilterSearch}
                placeholder="Search contacts..."
                value={props.value}
                ref={searchInput} />
        </div>
    )
}

export default searchBox