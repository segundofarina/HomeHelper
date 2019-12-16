import React from 'react'
import styles from './NoResultsSearch.module.css'
import Panel from '../../UI/Panel/Panel'
import img from '../../../assets/img/searchEmpty.png'

const noResultsSearch = () => (
    <Panel className={styles.Panel}>
        <img src={img} alt="" className={styles.Img} />
        <p className={styles.Description}>Lo sentimos, parece que no hay resultados para la busqueda...</p>
    </Panel>
)

export default noResultsSearch