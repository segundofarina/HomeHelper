import React from 'react'
import styles from './Vehicles.module.css'
import Camion from '../../../assets/img/Camion.png'
import CamionCemento from '../../../assets/img/CamionCemento.png'
import Topadora from '../../../assets/img/Topadora.png'
import Tractor from '../../../assets/img/Tractor.png'



const vehicleLoader = ()=>{
        return (
            <div className={styles.Loader}>
                <img className ={styles.Camion} src={Camion} alt=""></img>
                <img className ={styles.Topadora} src={Topadora} alt=""></img>
                <img className ={styles.Tractor} src={Tractor} alt=""></img>
                <img className ={styles.CamionCemento} src={CamionCemento} alt=""></img>
            </div>
        )
}

export default vehicleLoader