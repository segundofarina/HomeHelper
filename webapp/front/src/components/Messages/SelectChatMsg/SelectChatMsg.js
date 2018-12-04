import React from 'react'
import styles from './SelectChatMsg.module.css'
import defaultImg from '../../../assets/img/searchEmpty.png'

const selectChatMsg = () => (
    <div className={styles.SelectChatMsg}>
        <img className={styles.Img} src={defaultImg} alt="" />
        <p className={styles.Description}>Please select a chat...</p>
    </div>
)

export default selectChatMsg