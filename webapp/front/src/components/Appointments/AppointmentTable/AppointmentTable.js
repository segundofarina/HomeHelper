import React, { Fragment } from 'react'
import styles from './AppointmentTable.module.css'
import Panel from '../../UI/Panel/Panel'
import Table from '../../UI/Table/Table'
import Badge from '../../UI/Badge/Badge'
import Button from '../../UI/Button/Button'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEdit } from '@fortawesome/free-solid-svg-icons'
import emptyImg from '../../../assets/img/grua.png'

const appointmentTable = (props) => {
    const elemStyles = [props.className, styles.AppointmentTable]

    const rows = props.rows.map(row => {
        const columns = [...row.columns]
        columns[4] = (<Badge type={columns[4].type}>{columns[4].value}</Badge>)
        if(columns.length > 5) {
            if(columns[5] !== null) {
                const link = '/writeReview?id=' + columns[5]
                columns[5] = (
                    <Button btnImpl='Link' to={link} btnType='XSmall' btnColor='Blue'>
                        <FontAwesomeIcon icon={faEdit} className={styles.ReviewIcon} />
                        Write review
                    </Button>
                )
            }
       }
        return {
            id: row.id,
            columns: columns,
        }
    })

    let tableElem = (
        <div className={styles.TableContainer}>
            <Table headers={props.headers}
                    rows={rows} />
        </div>
    )

    if(rows.length === 0) {
        tableElem = (
            <Fragment>
                <img className={styles.Img} src={emptyImg} alt="" />
                <p className={styles.Description}>{props.emptyDescription}</p>
            </Fragment>
        )
    }

    return (
        <div className={elemStyles.join(' ')}>
            <Panel className={styles.Panel}>
                <div className={styles.Title}>
                    <h3>{props.title}</h3>
                </div>
                {tableElem}
           </Panel>
        </div>
    )
}

export default appointmentTable