import React from 'react'
import styles from './Badge.module.css'

const badge = (props) => {
    const badgeStyles = [styles.Badge, styles[props.type]]
    return (
        <span className={badgeStyles.join(' ')}>
            {props.children}
        </span>
    )
}

export default badge