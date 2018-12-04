import React from 'react'
import styles from './MessagesBox.module.css'
import MsgGroup from './MsgGroup/MsgGroup'

const messagesBox = React.forwardRef((props, ref) => {
    const sortedMsg = sortMsgByGroup(props.messages)

    return (
        <div className={styles.MessagesBox} ref={ref}>
            {sortedMsg.map(msgGroup => {
                return (<MsgGroup from={msgGroup.from} 
                            messages={msgGroup.messages}
                            key={msgGroup.groupId} />)
            })}
        </div>
    )
})

const sortMsgByGroup = (msgList) => {
    const sortedList = []
    let currentGroup = null

    msgList.forEach(msg => {
        /* Check if msg is from current group */
        if(currentGroup === null || msg.from !== currentGroup.from) {
            /* Save last group to sortedList */
            if(currentGroup) {
                sortedList.push(currentGroup)
            }

            /* Create new group */
            currentGroup = {
                groupId: msg.id,
                from: msg.from,
                messages: [{...msg}],
            }

        } else {
            /* Add msg to group */
            currentGroup.messages.push(msg)
        }
    });

    /* Add last group */
    if(currentGroup) {
        sortedList.push(currentGroup)
    }

    return sortedList
}

export default messagesBox