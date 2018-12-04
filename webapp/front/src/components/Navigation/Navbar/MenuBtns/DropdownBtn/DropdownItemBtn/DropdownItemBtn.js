import React from 'react'
import styles from './DropdownItemBtn.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons'
import { Link } from 'react-router-dom'

const dropdownItemBtn = (props) => {
    let icon = null
    if(props.icon === "logout") {
        icon = (<FontAwesomeIcon icon={faSignOutAlt} />)
    }
    return (
        <Link className={styles.dropdownItemBtn} to={props.to} onClick={props.onClick}>
            <span>{props.children}</span>
            {icon}
        </Link>
    )
}

export default dropdownItemBtn