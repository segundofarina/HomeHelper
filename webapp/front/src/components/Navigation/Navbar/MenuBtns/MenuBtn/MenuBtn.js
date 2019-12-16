import React from 'react'
import { NavLink } from 'react-router-dom'
import styles from './MenuBtn.module.css'

const menuBtn = (props) => {
    const elemStyles = [styles.MenuBtn]
    if(props.showingProvider) {
        elemStyles.push(styles.Provider)
    }

    return (
        <NavLink to={props.to} exact={props.exact} className={elemStyles.join(' ')} activeClassName={styles.active}>{props.children}</NavLink>
    )
}

export default menuBtn