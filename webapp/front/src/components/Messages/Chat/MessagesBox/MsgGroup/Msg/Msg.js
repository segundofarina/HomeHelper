import React from 'react'
import styles from './Msg.module.css'

const msg = (props) => {
    let msgStyles = [styles.Msg]
    if(props.from === 'mine') {
        msgStyles.push(styles.Mine)
    } else if(props.from === 'yours') {
        msgStyles.push(styles.Yours)
    }

    if(props.newMsg) {
        msgStyles.push(styles.AnimateNew)
    } else {
        msgStyles.push(styles.AnimateLoad)
    }

    return (
        <div className={msgStyles.join(' ')}>
            {props.text}
        </div>
    )
}

export default msg