import React from 'react'
import styles from './MsgInput.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons'

const msgInput = React.forwardRef((props, ref) => {
    return (
        <div className={styles.MsgInput}>
            <input type="text" 
                placeholder="Escribe un mensaje..." 
                onChange={props.msgChangeHandler}
                onKeyPress={event => {
                    if(event.key === 'Enter') {
                        props.sendBtnHandler()
                    }
                }}
                className={styles.Input} 
                value={props.msgValue}
                ref={ref}
                />
            <div className={styles.SendBtn}
                onClick={props.sendBtnHandler} >
                <FontAwesomeIcon icon={faPaperPlane} />
            </div>
        </div>
    )
})

export default msgInput