import React from 'react'
import styles from './ConnectionError.module.css'
import Panel from '../../UI/Panel/Panel'
import errorImg from '../../../assets/img/searchEmpty.png'
import Button from '../../UI/Button/Button'

/* if there is a props.reconnectHandler a reconnect btn is shown  */
const connectionError = (props) => {
    let reconnectBtn = null
    if(props.reconnectHandler) {
        reconnectBtn = (<Button onClick={props.reconnectHandler} btnType="Small" >Try Again</Button>)
    }

    return (
        <div className={styles.StatusContainer}>
            <Panel className={styles.Panel}>
                <img className={styles.Img} alt="" src={errorImg} />
                <p className={styles.Description}>Error while connecting to the server</p>
                {reconnectBtn}
            </Panel>
        </div>
    )
}

export default connectionError