import React from 'react'
import styles from './profile.module.css'
import Button from '../../UI/Button/Button'
import Panel from '../../UI/Panel/Panel'
import Rating from '../../UI/rating/Rating'
import defaultImg from '../../../assets/img/defaultProfile.png'

const profile = (props) => {

    let calification = (<p className={styles.emptyCalification}>Aun no hay calificaciones</p>)
    if(props.calification && props.calification !== 0) {
        calification = (<Rating value={props.calification} className={styles.calificationStars}/>)
    }

    return (
        <Panel className={styles.Panel}>
            <div className={styles.Col}>
                <div className={styles.ProfileImg}>
                <img src={props.img} 
                    onError={(ev)=>ev.target.src = defaultImg} 
                    className={styles.ProfilePicture}/>
                </div>
                <Button onClick={props.onClick} 
                    btnType='Small'
                    btnImpl='Link'
                    to={`/profile?id=${props.id}&addr=${props.searchedAddress}`}>
                    Ver Perfil
                </Button>
            </div>
            <div className={styles.InfoContainer}>
                <div className={styles.Title}>
                    <div className={styles.ProfileHeader}>
                        <h3>{props.name}</h3>
                        <span>&#x25CF;</span>
                        <h5>{props.serviceTypes.join(', ')}</h5>
                    </div>
                    {calification}
                </div>
                <div className={styles.Description}>
                    <p>{props.description}</p>
                </div>
            </div>
        </Panel>
    )
}

export default profile