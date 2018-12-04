import React from 'react'
import styles from './MsgGroup.module.css'
import Msg from './Msg/Msg'

const msgGroup = (props) => {
    let groupStyles = [styles.MsgGroup]
    if(props.from === 'mine') {
        groupStyles.push(styles.Mine)
    } else if(props.from === 'yours') {
        groupStyles.push(styles.Yours)
    }

    return (
        <div className={groupStyles.join(' ')}>
            {props.messages.map(msg => {
                return (<Msg from={props.from}
                            text={msg.text}
                            key={msg.id}
                            newMsg={msg.isNewMsg} />)
            })}
        </div>
    )
}

export default msgGroup