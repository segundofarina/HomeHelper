import React from 'react'
import styles from './progressBar.module.css'

const progressBar= (props) =>{
    const style = {width: props.value+"%"}
    let elemStyles = [styles.Progress]
    
    if(props.className) {
        elemStyles.push(props.className)
    }

    return(
        <div className={elemStyles.join(' ')}>
            <div className={styles.ProgressBar} style ={style} >
                {props.label}
            </div>
        </div>
    )
}
export default progressBar