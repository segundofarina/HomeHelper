import React from 'react'
import styles from './ContactItem.module.css'
import defaultImg from '../../../../assets/img/defaultProfile.png'

const contactItem = (props) => {

    let previewMsg = props.previewMsg.substr(0, 25)
    if(props.previewMsg && props.previewMsg.length > 25) {
        previewMsg += '...'
    }

    let contactItemStyles = [styles.ContactItem]
    if(props.active) {
        contactItemStyles.push(styles.Active)
    }

    return (
        <div className={contactItemStyles.join(' ')} onClick={props.onClick}>
            <div className={styles.Wrap}>
            <img src={props.img} 
            onError={(ev)=>ev.target.src = defaultImg} 
            className={styles.ProfilePicture}/>
                <div className={styles.ContactInfo}>
                    <p className={styles.Name}>
                        {props.name}
                    </p>
                    <p className={styles.Msg}>
                        {previewMsg}
                    </p>
                </div>
            </div>
        </div>
    )
}

export default contactItem