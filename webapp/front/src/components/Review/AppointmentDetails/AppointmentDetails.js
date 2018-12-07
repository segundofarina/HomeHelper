import React from 'react'
import styles from './AppointmentDetails.module.css'
import Panel from '../../UI/Panel/Panel'
import defaultImg from '../../../assets/img/defaultProfile.png'
import Rating from '../../UI/rating/Rating'

const appointmentDetails = (props) => {
    const panelStyles = [styles.Panel]
    if(props.panelStyles) {
        panelStyles.push(props.panelStyles)
    }

    return (
        <Panel className={panelStyles.join(' ')}>
            <div className={styles.Container} >
                <img src={props.img} alt=""
                        onError={(ev)=>ev.target.src = defaultImg} 
                        className={styles.ProfilePicture}/>
                <h3 className={styles.ProviderName}>{props.name}</h3>
                <Rating value={props.calification} />
                <div className={styles.AppSection}>
                    <h5>Service Type:</h5>
                    <p>{props.serviceType}</p>
                </div>
                <div className={styles.AppSection}>
                    <h5>Date:</h5>
                    <p>{props.date}</p>
                </div>
                <div className={styles.AppSection}>
                    <h5>Description:</h5>
                    <p>{props.description}</p>
                </div>
            </div>
        </Panel>
    )
}

export default appointmentDetails