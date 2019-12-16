import React from 'react'
import styles from './ProfileDetails.module.css'
import Panel from '../../../../UI/Panel/Panel'
import defaultImage from '../../../../../assets/img/defaultProfile.png'
import Rating from '../../../../UI/rating/Rating'

const profileDetails = (props) => {
    return (
        <Panel className={styles.ProfileDetails}>
            <img src={props.img} 
                        onError={(ev)=>ev.target.src = defaultImage} 
                        className={styles.ProfileImage}/>
            <p className={styles.Name}>{props.name}</p>
            <Rating value={props.generalCalification} />
            <p className={styles.GeneralCalification}>General Calification: <strong>{props.generalCalification}</strong></p>
        </Panel>
    )
}

export default profileDetails