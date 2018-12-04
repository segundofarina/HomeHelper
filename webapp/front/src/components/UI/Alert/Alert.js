import React from 'react'
import styles from './Alert.module.css'

const alert = (props) => {
    const elemStyles = [styles.Alert, props.className, styles[props.type]]
    return (
        <div className={elemStyles.join(' ')}>
            {props.children}
        </div>
    )
}

export default alert