import React from 'react'
import styles from './AppointmentsTable.module.css'
import Panel from '../../../UI/Panel/Panel'
import Table from '../../../UI/Table/Table'

const appointmentTable = (props) => {
    const elemStyles = [props.className, styles.AppointmentTable]

    return (
        <div className={elemStyles.join(' ')}>
            <Panel>
                <div className={styles.Title}>
                    <h3>{props.title}</h3>
                </div>
                <div className={styles.TableContainer}>
                    <Table headers={props.headers}
                            rows={props.rows} />
                </div>
            </Panel>
        </div>
    )
}

export default appointmentTable