import React from 'react'
import styles from './Table.module.css'

/* props.headers is an array of headers. ie: ['Column 1', 'Column 2', 'Column 3'] 
 * props.rows is an array of arrays. ie: 
 *   [{
 *      id: 1, 
 *      columns: ['Column 1', 'Column 2', 'Column 3'],
 *    },{
 *      id: 2, 
 *      columns: ['Column 1', 'Column 2', 'Column 3'],
 *    },{
 *      id: 3, 
 *      columns: ['Column 1', 'Column 2', 'Column 3'],
 *    }]
*/
const table = (props) => {
    const headers = props.headers.map((header, idx) => {
        return (
            <th key={idx}>{header}</th>
        )
    })

    const rows = props.rows.map(row => {
        return (
            <tr key={row.id}>
                {row.columns.map((column, idx) => {
                    return (
                        <td key={idx}>{column}</td>
                    )
                })}
            </tr>
        )
    })

    return (
        <table className={styles.Table}>
            <thead>
                <tr>
                    {headers}
                </tr>
            </thead>
            <tbody>
                {rows}
            </tbody>
        </table>
    )
}

export default table